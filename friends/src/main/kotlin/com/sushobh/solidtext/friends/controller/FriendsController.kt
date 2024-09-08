package com.sushobh.solidtext.friends.controller

import com.sushobh.solidtext.apiclasses.FriendServiceClasses
import com.sushobh.solidtext.auth.EXTRA_USER
import com.sushobh.solidtext.auth.api.AuthService

import com.sushobh.solidtext.com.sushobh.solidtext.friends.FriendsService
import com.sushobh.solidtext.apiclasses.STFrenRequest
import com.sushobh.solidtext.apiclasses.STReceivedFrenRequest
import com.sushobh.solidtext.apiclasses.STSentFrenRequest
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
    ): STResponse<FriendServiceClasses.FrenReqResult> {
        return authService.getAuthUserChain<FriendServiceClasses.FrenReqActionInput, FriendServiceClasses.FrenReqResult>(headers,body)
            .addItem { input, _ ->
                STResponse(friendsService.onFrenReqAction(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }

    @GetMapping("/frens/freReqs")
    suspend fun getFrenReqs(@RequestBody body : FriendServiceClasses.FrenReqActionInput,
                            @RequestHeader headers: Map<String, String>
    ): STResponse<FriendServiceClasses.FrenReqResult> {
        return authService.getAuthUserChain<FriendServiceClasses.FrenReqActionInput, FriendServiceClasses.FrenReqResult>(headers,body)
            .addItem { input, _ ->
                STResponse(friendsService.onFrenReqAction(body,input.getExtra(EXTRA_USER)), null)
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
    suspend fun getSentFriendRequests(@RequestHeader headers: Map<String, String>) : STResponse<List<STSentFrenRequest>>{
        return authService.getAuthUserChain<Any,List<STSentFrenRequest>>(headers,Unit)
            .addItem { input, _ ->
                STResponse(friendsService.getSentRequests(input[EXTRA_USER]), null)
            } .next()
    }

    @GetMapping("/frens/receivedRequests")
    suspend fun getReceivedFriendRequests(@RequestHeader headers: Map<String, String>) : STResponse<List<STReceivedFrenRequest>>{
        return authService.getAuthUserChain<Any,List<STReceivedFrenRequest>>(headers,Unit)
            .addItem { input, _ ->
                STResponse(friendsService.getReceivedRequests(input[EXTRA_USER]), null)
            } .next()
    }


}