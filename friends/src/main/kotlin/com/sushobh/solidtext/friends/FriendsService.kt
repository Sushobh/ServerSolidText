package com.sushobh.solidtext.com.sushobh.solidtext.friends

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.apiclasses.FriendServiceClasses
import com.sushobh.solidtext.apiclasses.STUser
import com.sushobh.solidtext.auth.api.AuthService
import com.sushobh.solidtext.apiclasses.FrenReqStatus
import com.sushobh.solidtext.apiclasses.FrenReqStatus.*
import com.sushobh.solidtext.apiclasses.FrenReqStatus.Nothing
import com.sushobh.solidtext.apiclasses.STFrenRequest
import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.ETConnectionReq
import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.EtFrenConnection
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConRepo
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConReqRepo
import org.springframework.stereotype.Service
import java.math.BigInteger

//TODO making adding friends transactional
//TODO make view match with the repository
@Service
open class FriendsService(
    private val dateUtil: DateUtil,
    private val etConReqRepo: ETConReqRepo,
    private val etConRepo: ETConRepo,
    private val authService: AuthService
) {


    fun onFrenReqAction(actionInput: FriendServiceClasses.FrenReqActionInput, user: STUser): FriendServiceClasses.FrenReqResult {

        val fromUserId = user.userId
        val toUserId = actionInput.toUserId
        if (areFriends(fromUserId, toUserId)) {
            return FriendServiceClasses.FrenReqResult.Failed("Already friends")
        }
        val existingConnectionRequest = etConReqRepo.getExistingConnectionRequest(user.userId, actionInput.toUserId)
        val existingConnectionRequestFromRecipient =
            etConReqRepo.getExistingConnectionRequest(actionInput.toUserId, user.userId)

        existingConnectionRequestFromRecipient?.let {
            when (frenReqStatusFromText(it.status)) {
                Accepted -> {
                    return FriendServiceClasses.FrenReqResult.Failed("Already friends")
                }

                Nothing -> {}
                Refused -> {}
                Sent -> {
                    addFriends(fromUserId, toUserId)
                    return FriendServiceClasses.FrenReqResult.FriendAdded
                }

                InActive -> {}
            }
        }

        existingConnectionRequest?.let {
            when (frenReqStatusFromText(it.status)) {
                Accepted -> {
                    return FriendServiceClasses.FrenReqResult.Failed("Already friends")
                }

                Nothing -> {}
                FrenReqStatus.Refused -> {}
                Sent -> {
                    return FriendServiceClasses.FrenReqResult.Failed("Friend request already pending")
                }

                FrenReqStatus.InActive -> {}
            }
        }
        val newRequest = ETConnectionReq(dateUtil.getCurrentTime(), Sent.name!!, fromUserId, toUserId)
        etConReqRepo.save(newRequest)
        return FriendServiceClasses.FrenReqResult.RequestSent("Request sent")
    }

    private fun areFriends(id: BigInteger, id2: BigInteger): Boolean {
        val existingFriends = etConRepo.getExistingConnections(id, id2)

        return existingFriends.isNotEmpty()
    }


    private fun addFriends(from: BigInteger, to: BigInteger) {
        val etCon = EtFrenConnection(dateUtil.getCurrentTime(), from, to)
        etConReqRepo.deActivateRequest(InActive.name!!, from, to)
        etConReqRepo.deActivateRequest(InActive.name!!, to, from)
        etConRepo.save(etCon)
    }

    private fun frenReqStatusFromText(text: String): FrenReqStatus {
        return when (text) {
            Sent.name -> Sent
            Accepted.name -> Accepted
            FrenReqStatus.Refused.name -> FrenReqStatus.Refused
            else -> Nothing
        }
    }

    suspend fun searchUserByName(frenSearchUserByNameInput: FriendServiceClasses.FrenSearchUserByNameInput, user: STUser): FriendServiceClasses.FrenSearchStatus {
        val searchedFriend = authService.getUserByUserName(frenSearchUserByNameInput.userName)
        searchedFriend?.let {
            return FriendServiceClasses.FrenSearchStatus.Found(
                FriendServiceClasses.FrenReqSendingAbility(
                    canFriendRequestBeSentToUser(searchedFriend, user)
                ),searchedFriend)
        }
        return FriendServiceClasses.FrenSearchStatus.NotFound
    }


    private fun canFriendRequestBeSentToUser(user: STUser, from: STUser): Boolean {
        val areFriends = areFriends(user.userId, from.userId)
        return !areFriends
    }

    fun getSentRequests(user : STUser): List<STFrenRequest> {
        return etConReqRepo.getSentRequests(user.userId)
    }

    fun getReceivedRequests(user: STUser): List<STFrenRequest>? {
         return etConReqRepo.getReceivedRequests(user.userId)
    }

}