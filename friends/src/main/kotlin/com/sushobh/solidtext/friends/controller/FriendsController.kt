package com.sushobh.solidtext.friends.controller

import com.sushobh.solidtext.apiclasses.*
import com.sushobh.solidtext.auth.EXTRA_USER
import com.sushobh.solidtext.auth.api.AuthService

import com.sushobh.solidtext.com.sushobh.solidtext.friends.FriendsService
import common.util.requests.STResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController


@RestController
class FriendsController(private val friendsService: FriendsService, private val authService : AuthService) {

    @PostMapping("/frens/reqAction")
    suspend fun frenReqAction(@RequestBody body : FriendServiceClasses.FrenReqActionInput,
                              @RequestHeader headers: Map<String, String>
    ): STResponse<FriendServiceClasses.FrenReqActionResult> {
        return authService.getAuthUserChain<FriendServiceClasses.FrenReqActionInput, FriendServiceClasses.FrenReqActionResult>(headers,body)
            .addItem { input, _ ->
                STResponse(friendsService.onFrenReqAction(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }


    @PostMapping("/frens/sendFrenReq")
    suspend fun frenReqAction(@RequestBody body : FriendServiceClasses.FrenReqSendInput,
                              @RequestHeader headers: Map<String, String>
    ): STResponse<FriendServiceClasses.FrenReqActionResult> {
        return authService.getAuthUserChain<FriendServiceClasses.FrenReqSendInput, FriendServiceClasses.FrenReqActionResult>(headers,body)
            .addItem { input, _ ->
                STResponse(friendsService.onSendFrenReq(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }


    @PostMapping("/frens/searchByUserName")
    suspend fun searchUserByName(@RequestBody body : FriendServiceClasses.FrenSearchUserByNameInput, @RequestHeader headers: Map<String, String>) :
            STResponse<FriendServiceClasses.FrenSearchStatus> {
        return authService.getAuthUserChain<FriendServiceClasses.FrenSearchUserByNameInput, FriendServiceClasses.FrenSearchStatus>(headers,body)
            .addItem { input, _ ->
                STResponse(friendsService.searchUserByName(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }

    @GetMapping("/frens/sentFriendRequests")
    suspend fun getSentFriendRequests(@RequestHeader headers: Map<String, String>) : STResponse<SentFriendRequestListStatus>{
        return authService.getAuthUserChain<Any,SentFriendRequestListStatus>(headers,Unit)
            .addItem { input, _ ->
                STResponse(friendsService.getSentRequests(input[EXTRA_USER]), null)
            } .next()
    }

    @GetMapping("/frens/receivedRequests")
    suspend fun getReceivedFriendRequests(@RequestHeader headers: Map<String, String>) : STResponse<ReceivedFriendRequestListStatus>{
        return authService.getAuthUserChain<Any,ReceivedFriendRequestListStatus>(headers,Unit)
            .addItem { input, _ ->
                STResponse(friendsService.getReceivedRequests(input[EXTRA_USER]), null)
            } .next()
    }

    @GetMapping("/frens/getFriends")
    suspend fun getFriends(@RequestHeader headers: Map<String, String>) : STResponse<FriendListStatus>{
        return authService.getAuthUserChain<Any,FriendListStatus>(headers,Unit)
            .addItem { input, _ ->
                STResponse(friendsService.getFriends(input[EXTRA_USER]), null)
            } .next()
    }


}