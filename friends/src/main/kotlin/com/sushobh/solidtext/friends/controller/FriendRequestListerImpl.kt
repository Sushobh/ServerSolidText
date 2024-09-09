package com.sushobh.solidtext.friends.controller

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.apiclasses.*
import com.sushobh.solidtext.auth.api.AuthService
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConRepo
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConReqRepo
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service


interface FriendRequestLister {
    suspend fun getSentRequests(user : STUser): List<STSentFrenRequest>
    suspend fun getReceivedRequests(user: STUser): List<STReceivedFrenRequest>
}



@Service
class FriendRequestListerImpl(private val dateUtil: DateUtil,
                              private val etConReqRepo: ETConReqRepo,
                              private val etConRepo: ETConRepo,
                              private val authService: AuthService
)  : FriendRequestLister {
    override suspend fun getSentRequests(user : STUser): List<STSentFrenRequest> {

        val list =  etConReqRepo.getSentFrenRequestsByUserForStatus(user.userId,FrenReqStatus.Sent.name!!).map { req ->
            return@map object : STSentFrenRequest {
                override val req: STFrenRequest = req
                override val receiverUser: STUser? = runBlocking { authService.getUserByid(req.receiverId) }
            }
        }

        return list
    }

    override suspend fun getReceivedRequests(user: STUser): List<STReceivedFrenRequest> {

        val list =  etConReqRepo.getReceiveRequestsByUserForStatus(user.userId,FrenReqStatus.Sent.name!!).map { req ->
            return@map object : STReceivedFrenRequest {
                override val req: STFrenRequest = req
                override val senderUser: STUser? = runBlocking { authService.getUserByid(req.senderId) }
            }
        }
        return list
    }

}