package com.sushobh.solidtext.friends.controller

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.apiclasses.*
import com.sushobh.solidtext.auth.api.AuthService
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConRepo
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConReqRepo
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service


interface FriendRequestLister {
    suspend fun getSentRequests(user : STUser): SentFriendRequestListStatus
    suspend fun getReceivedRequests(user: STUser): ReceivedFriendRequestListStatus
    suspend fun getFriends(user : STUser) : FriendListStatus
}



@Service
class FriendRequestListerImpl(
                              private val etConReqRepo: ETConReqRepo,
                              private val authService: AuthService,
    private val etConRepo : ETConRepo
)  : FriendRequestLister {




    override suspend fun getSentRequests(user : STUser): SentFriendRequestListStatus {

        val list =  etConReqRepo.getSentFrenRequestsByUserForStatus(user.userId,FrenReqStatus.Sent().name!!).map { req ->
            return@map object : ISTSentFrenRequest {
                override val req: ISTFrenRequest = req
                override val receiverUser: STUser? = runBlocking { authService.getUserByid(req.receiverId) }
            }
        }

        return SentFriendRequestListStatus.Success(list)
    }

    override suspend fun getReceivedRequests(user: STUser): ReceivedFriendRequestListStatus {

        val list =  etConReqRepo.getReceiveRequestsByUserForStatus(user.userId,FrenReqStatus.Sent().name!!).map { req ->
            return@map object : ISTReceivedFrenRequest {
                override val req: ISTFrenRequest = req
                override val senderUser: STUser? = runBlocking { authService.getUserByid(req.senderId) }
            }
        }
        return ReceivedFriendRequestListStatus.Success(list)
    }

    override suspend fun getFriends(user: STUser): FriendListStatus {
        val list = etConRepo.getFriendConnectionsForUser(user.userId)
        return list.map {
            val friendId = if(it.fromUserId == user.userId){
                it.toUserid
            }
            else {
                it.fromUserId
            }
            authService.getUserByid(friendId)
        }.run {
            FriendListStatus.Success(this.filterNotNull())
        }
    }

}