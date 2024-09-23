package com.sushobh.solidtext.com.sushobh.solidtext.friends

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.apiclasses.*
import com.sushobh.solidtext.auth.api.AuthService
import com.sushobh.solidtext.apiclasses.FrenReqStatus.*
import com.sushobh.solidtext.apiclasses.FrenReqStatus.Nothing
import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.ETConnectionReq
import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.EtFrenConnection
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConRepo
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConReqRepo
import com.sushobh.solidtext.friends.controller.FriendRequestHandler
import com.sushobh.solidtext.friends.controller.FriendRequestLister
import com.sushobh.solidtext.friends.controller.FriendsUtil
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

import java.math.BigInteger
import kotlin.jvm.optionals.getOrNull

//TODO making adding friends transactional
//TODO make view match with the repository
@Service
open class FriendsService(
    private val etConRepo: ETConRepo,
    private val authService: AuthService,
    private val friendRequestHandler: FriendRequestHandler,
    private val friendRequestLister: FriendRequestLister,
    private val friendsUtil: FriendsUtil
) : FriendRequestHandler by friendRequestHandler,FriendRequestLister by friendRequestLister {





    suspend fun searchUserByName(frenSearchUserByNameInput: FriendServiceClasses.FrenSearchUserByNameInput, user: STUser): FriendServiceClasses.FrenSearchStatus {
        val searchedFriend = authService.getUserByUserName(frenSearchUserByNameInput.userName)
        searchedFriend?.let {
            return FriendServiceClasses.FrenSearchStatus.Found(
                FriendServiceClasses.FrenReqSendingAbility(
                    canFriendRequestBeSentToUser(searchedFriend, user)
                ),searchedFriend)
        }
        return FriendServiceClasses.FrenSearchStatus.NotFound()
    }


    private suspend fun canFriendRequestBeSentToUser(user: STUser, from: STUser): Boolean {
        val areFriends = friendsUtil.areFriends(user.userId, from.userId)
        return !areFriends
    }

}