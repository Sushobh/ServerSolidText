package com.sushobh.solidtext.friends.controller

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.apiclasses.FrenReqAction
import com.sushobh.solidtext.apiclasses.FrenReqStatus.*
import com.sushobh.solidtext.apiclasses.FrenReqStatus.Nothing
import com.sushobh.solidtext.apiclasses.FriendServiceClasses
import com.sushobh.solidtext.apiclasses.STUser
import com.sushobh.solidtext.auth.api.AuthService
import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.ETConnectionReq
import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.EtFrenConnection
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConRepo
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConReqRepo
import com.sushobh.solidtext.friends.frenReqStatusFromText
import com.sushobh.solidtext.friends.toFrenReqAction
import org.springframework.stereotype.Service
import java.math.BigInteger
import kotlin.jvm.optionals.getOrNull


interface FriendRequestHandler {

    suspend fun onFrenReqAction(
        actionInput: FriendServiceClasses.FrenReqActionInput,
        user: STUser
    ): FriendServiceClasses.FrenReqActionResult

    suspend fun onSendFrenReq(actionInput: FriendServiceClasses.FrenReqSendInput,user : STUser) : FriendServiceClasses.FrenReqActionResult

}

@Service
class FriendRequestHandlerImpl(
    private val dateUtil: DateUtil,
    private val etConReqRepo: ETConReqRepo,
    private val etConRepo: ETConRepo,
    private val authService: AuthService,
    private val friendsUtil: FriendsUtil
) : FriendRequestHandler {


    override suspend fun onFrenReqAction(
        actionInput: FriendServiceClasses.FrenReqActionInput,
        user: STUser
    ): FriendServiceClasses.FrenReqActionResult {


        return when (actionInput.action.toFrenReqAction()) {
            is FrenReqAction.Accept -> handleAccept(actionInput, user)
            is FrenReqAction.Nothing -> FriendServiceClasses.FrenReqActionResult.Failed("Invalid friend request action.")
            is FrenReqAction.Refuse -> {
                handleRefuseRequest(actionInput, user)
            }

            is FrenReqAction.CancelRequest -> {
                cancelRequest(actionInput, user)
            }

        }
    }

    override suspend fun onSendFrenReq(
        actionInput: FriendServiceClasses.FrenReqSendInput,
        user: STUser
    ): FriendServiceClasses.FrenReqActionResult {
        val fromUserId = user.userId
        val toUserId = actionInput.toUserId
        if (friendsUtil.areFriends(fromUserId, toUserId)) {
            return FriendServiceClasses.FrenReqActionResult.Failed("Already friends")
        }
        val existingConnectionRequest = etConReqRepo.getExistingConnectionRequest(user.userId, actionInput.toUserId)
        val existingConnectionRequestFromRecipient =
            etConReqRepo.getExistingConnectionRequest(actionInput.toUserId, user.userId)

        existingConnectionRequestFromRecipient?.let {
            when (it.status.frenReqStatusFromText()) {
                is Accepted -> {
                    return FriendServiceClasses.FrenReqActionResult.Failed("Already friends")
                }

                is Nothing -> {}
                is Refused -> {

                }

                is Sent -> {
                    acceptRequest(fromUserId, toUserId)
                    return FriendServiceClasses.FrenReqActionResult.FriendAdded()
                }

                is InActive -> {}
            }
        }

        existingConnectionRequest?.let {
            when (it.status.frenReqStatusFromText()) {
                is Accepted -> {
                    return FriendServiceClasses.FrenReqActionResult.Failed("Already friends")
                }

                is Nothing -> {

                }

                is Refused -> {
                    //If had been refused, we allow the user to send it again
                }

                is Sent -> {
                    return FriendServiceClasses.FrenReqActionResult.Failed("Friend request already pending")
                }

                is InActive -> {}
            }
        }
        val newRequest = ETConnectionReq(dateUtil.getCurrentTime(), Sent().name!!, fromUserId, toUserId)
        etConReqRepo.save(newRequest)
        return FriendServiceClasses.FrenReqActionResult.RequestSent("Request sent")
    }


    suspend fun cancelRequest(
        actionInput: FriendServiceClasses.FrenReqActionInput,
        user: STUser
    ): FriendServiceClasses.FrenReqActionResult {
        val request = actionInput.requestId?.let { etConReqRepo.findById(it).getOrNull() }
        request?.let {
            if(request.fromUserId != user.userId){
                return FriendServiceClasses.FrenReqActionResult.Failed("Request does not belong to the user")
            }
            when(request.status.frenReqStatusFromText()){
                is Accepted -> {}
                is InActive -> {}
                is Nothing -> {
                    cancelRequest(user.userId,request.toUserid)
                    return FriendServiceClasses.FrenReqActionResult.Cancelled()
                }
                is Refused -> {}
                is Sent -> {
                    cancelRequest(user.userId,request.toUserid)
                    return FriendServiceClasses.FrenReqActionResult.Cancelled()
                }
            }
        }

        return FriendServiceClasses.FrenReqActionResult.Failed("Invalid state")
    }

    suspend fun handleRefuseRequest(
        actionInput: FriendServiceClasses.FrenReqActionInput,
        user: STUser
    ): FriendServiceClasses.FrenReqActionResult {
        val request = actionInput.requestId?.let { etConReqRepo.findById(it).getOrNull() }

        request?.let {
            if(request.toUserid != user.userId){
                return FriendServiceClasses.FrenReqActionResult.Failed("Request does not belong to the user")
            }
            val status = it.status.frenReqStatusFromText()
            val fromUserId = request.fromUserId
            val toUserId = request.toUserid
            when (status) {
                is Accepted -> {
                    return FriendServiceClasses.FrenReqActionResult.Failed("Invalid request, the request was accepted earlier.")
                }

                is InActive -> {
                    return FriendServiceClasses.FrenReqActionResult.Failed("InActive request")
                }

                is Nothing -> {
                    refuseRequest(fromUserId, toUserId)
                    return FriendServiceClasses.FrenReqActionResult.Refused("Refused")
                }

                is Refused -> {
                    return FriendServiceClasses.FrenReqActionResult.Failed("Request was refused earlier")
                }

                is Sent -> {
                    refuseRequest(fromUserId, toUserId)
                    return FriendServiceClasses.FrenReqActionResult.Refused("Refused")
                }
            }
        }
        return FriendServiceClasses.FrenReqActionResult.Failed("Request does not exist or its invalid")
    }


    suspend fun handleAccept(
        actionInput: FriendServiceClasses.FrenReqActionInput,
        user: STUser
    ): FriendServiceClasses.FrenReqActionResult {
        val request = actionInput.requestId?.let { etConReqRepo.findById(it).getOrNull() }
        request?.let {
            val status = it.status.frenReqStatusFromText()
            when (status) {
                is Accepted -> {
                    return FriendServiceClasses.FrenReqActionResult.Failed("Already friends")
                }

                is InActive -> {
                    return FriendServiceClasses.FrenReqActionResult.Failed("InActive request")
                }

                is Nothing -> {
                    acceptRequest(it.fromUserId, it.toUserid)
                    return FriendServiceClasses.FrenReqActionResult.FriendAdded()
                }

                is Refused -> {
                    return FriendServiceClasses.FrenReqActionResult.Failed("Request was refused earlier")
                }

                is Sent -> {
                    acceptRequest(it.fromUserId, it.toUserid)
                    return FriendServiceClasses.FrenReqActionResult.FriendAdded()
                }
            }
        }
        return FriendServiceClasses.FrenReqActionResult.Failed("Request does not exist or its invalid")
    }


    private fun cancelRequest(from: BigInteger, to: BigInteger) {
        etConReqRepo.updateStatusOfRequest(InActive().name!!, from, to)
    }


    private fun refuseRequest(from: BigInteger, to: BigInteger) {
        etConReqRepo.updateStatusOfRequest(Refused().name!!, from, to)
        etConReqRepo.updateStatusOfRequest(InActive().name!!, to, from)
    }

    private fun acceptRequest(from: BigInteger, to: BigInteger) {
        val etCon = EtFrenConnection(dateUtil.getCurrentTime(), from, to)
        etConReqRepo.updateStatusOfRequest(Accepted().name!!, from, to)
        etConReqRepo.updateStatusOfRequest(InActive().name!!, to, from)
        etConRepo.save(etCon)
    }


}