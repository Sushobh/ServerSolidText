package com.sushobh.solidtext.posts.controllers

import com.sushobh.solidtext.auth.EXTRA_USER
import com.sushobh.solidtext.auth.api.AuthService
import com.sushobh.solidtext.auth.api.STUser
import com.sushobh.solidtext.posts.PostsService
import com.sushobh.solidtext.posts.api.STPost
import com.sushobh.solidtext.posts.repos.PJPostFeedItem
import common.util.requests.STResponse
import org.springframework.web.bind.annotation.*


@RestController
internal class PostsController(private val authService : AuthService,private val postsService: PostsService) {

    @PostMapping("/posts/createPost")
    suspend fun createPost(@RequestBody body : PostsService.CreatePostInput,
                           @RequestHeader headers: Map<String, String>
    ): STResponse<PostsService.CreatePostStatus> {
        return authService.getAuthUserChain<PostsService.CreatePostInput,PostsService.CreatePostStatus>(headers,body)
            .addItem { input, _ ->
                STResponse(postsService.createPost(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }


    @GetMapping("/posts/feed")
    suspend fun getPostFeed(@RequestHeader headers: Map<String, String>) : STResponse<List<STPost>>{
        return authService.getAuthUserChain<Any,List<STPost>>(headers,Unit)
            .addItem { input, _ ->
                STResponse(postsService.getPostFeed(input[EXTRA_USER]), null)
            } .next()
    }



}