package com.sushobh.solidtext.com.sushobh.solidtext.friends

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.auth.api.AuthService
import com.sushobh.solidtext.auth.api.STUser
import com.sushobh.solidtext.com.sushobh.solidtext.friends.api.FrenReqStatus
import com.sushobh.solidtext.com.sushobh.solidtext.friends.api.FrenReqStatus.Accepted
import com.sushobh.solidtext.com.sushobh.solidtext.friends.api.FrenReqStatus.Nothing
import com.sushobh.solidtext.com.sushobh.solidtext.friends.api.FrenReqStatus.Sent
import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.ETConnectionReq
import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.EtFrenConnection
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConRepo
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConReqRepo
import org.springframework.stereotype.Service
import java.math.BigInteger

//TODO making adding friends transactional
@Service
class FriendsService(private val dateUtil: DateUtil,
                     private val etConReqRepo: ETConReqRepo,
                     private val etConRepo: ETConRepo,
                     private val authService: AuthService) {

    data class FrenReqActionInput(val toUserId: BigInteger, val action : String)


    sealed class FrenReqResult(val status : String?) {
        data class RequestSent(val message: String? = null) : FrenReqResult(RequestSent::class.simpleName)
        data object FriendAdded : FrenReqResult(FriendAdded::class.simpleName)
        data class Failed(val message : String) : FrenReqResult(Failed::class.simpleName)
    }


    fun onFrenReqAction(actionInput: FrenReqActionInput, user: STUser) : FrenReqResult{

        val fromUserId = user.userId
        val toUserId = actionInput.toUserId
        if(areFriends(fromUserId,toUserId)){
            return FrenReqResult.Failed("Already friends")
        }
        val existingConnectionRequest = etConReqRepo.getExistingConnectionRequest(user.userId,actionInput.toUserId)
        val existingConnectionRequestFromRecipient = etConReqRepo.getExistingConnectionRequest(actionInput.toUserId,user.userId)

        existingConnectionRequestFromRecipient?.let {
            when(frenReqStatusFromText(it.status)) {
                Accepted -> {
                    return FrenReqResult.Failed("Already friends")
                }
                Nothing -> {}
                FrenReqStatus.Refused -> {}
                Sent -> {
                    addFriends(fromUserId,toUserId)
                    return FrenReqResult.FriendAdded
                }
            }
        }

        existingConnectionRequest?.let {
            when(frenReqStatusFromText(it.status)) {
                Accepted -> {
                    return FrenReqResult.Failed("Already friends")
                }
                Nothing -> {}
                FrenReqStatus.Refused -> {}
                Sent -> {
                    return FrenReqResult.Failed("Friend request already pending")
                }
            }
        }
        val newRequest = ETConnectionReq(dateUtil.getCurrentTime(),Sent.name!!,fromUserId,toUserId)
        etConReqRepo.save(newRequest)
        return FrenReqResult.RequestSent("Request sent")
    }

    private fun areFriends(id : BigInteger, id2 : BigInteger) : Boolean{
        val existingFriends = etConRepo.getExistingConnections(id,id2)
        return existingFriends.isNotEmpty()
    }

    private fun addFriends(from : BigInteger,to : BigInteger) {
        val etCon = EtFrenConnection(dateUtil.getCurrentTime(),from,to)
        etConRepo.save(etCon)
    }

    private fun frenReqStatusFromText(text : String) : FrenReqStatus{
        return when(text){
            Sent.name -> Sent
            Accepted.name -> Accepted
            FrenReqStatus.Refused.name -> FrenReqStatus.Refused
            else -> Nothing
        }
    }

}