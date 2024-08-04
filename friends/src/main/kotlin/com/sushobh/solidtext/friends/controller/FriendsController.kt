package com.sushobh.solidtext.friends.controller

import com.sushobh.solidtext.auth.EXTRA_USER
import com.sushobh.solidtext.auth.api.AuthService

import com.sushobh.solidtext.com.sushobh.solidtext.friends.FriendsService
import com.sushobh.solidtext.com.sushobh.solidtext.friends.api.STFrenRequest
import common.util.requests.STResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController


@RestController
class FriendsController(private val friendsService: FriendsService, private val authService : AuthService) {

    @PostMapping("/frens/reqAction")
    suspend fun frenReqAction(@RequestBody body : FriendsService.FrenReqActionInput,
                           @RequestHeader headers: Map<String, String>
    ): STResponse<FriendsService.FrenReqResult> {
        return authService.getAuthUserChain<FriendsService.FrenReqActionInput,FriendsService.FrenReqResult>(headers,body)
            .addItem { input, _ ->
                STResponse(friendsService.onFrenReqAction(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }

    @GetMapping("/frens/freReqs")
    suspend fun getFrenReqs(@RequestBody body : FriendsService.FrenReqActionInput,
                              @RequestHeader headers: Map<String, String>
    ): STResponse<FriendsService.FrenReqResult> {
        return authService.getAuthUserChain<FriendsService.FrenReqActionInput,FriendsService.FrenReqResult>(headers,body)
            .addItem { input, _ ->
                STResponse(friendsService.onFrenReqAction(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }


    @PostMapping("/frens/searchByUserName")
    suspend fun searchUserByName(@RequestBody body : FriendsService.FrenSearchUserByNameInput,@RequestHeader headers: Map<String, String>) :
            STResponse<FriendsService.FrenSearchStatus> {
        return authService.getAuthUserChain<FriendsService.FrenSearchUserByNameInput,FriendsService.FrenSearchStatus>(headers,body)
            .addItem { input, _ ->
                STResponse(friendsService.searchUserByName(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }

    @GetMapping("/frens/sentFriendRequests")
    suspend fun getSentFriendRequests(@RequestHeader headers: Map<String, String>) : STResponse<List<STFrenRequest>>{
        return authService.getAuthUserChain<Any,List<STFrenRequest>>(headers,Unit)
            .addItem { input, _ ->
                STResponse(friendsService.getSentRequests(input[EXTRA_USER]), null)
            } .next()
    }

    @GetMapping("/frens/receivedRequests")
    suspend fun getReceivedFriendRequests(@RequestHeader headers: Map<String, String>) : STResponse<List<STFrenRequest>>{
        return authService.getAuthUserChain<Any,List<STFrenRequest>>(headers,Unit)
            .addItem { input, _ ->
                STResponse(friendsService.getReceivedRequests(input[EXTRA_USER]), null)
            } .next()
    }


}