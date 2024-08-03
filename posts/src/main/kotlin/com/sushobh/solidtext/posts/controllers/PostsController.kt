package com.sushobh.solidtext.posts.controllers

import com.sushobh.solidtext.auth.EXTRA_USER
import com.sushobh.solidtext.auth.api.AuthService
import com.sushobh.solidtext.auth.api.STUser
import com.sushobh.solidtext.posts.PostsService
import common.util.requests.STResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController


@RestController
class PostsController(private val authService : AuthService,private val postsService: PostsService) {

    @PostMapping("/posts/createPost")
    suspend fun createPost(@RequestBody body : PostsService.CreatePostInput,
                           @RequestHeader headers: Map<String, String>
    ): STResponse<PostsService.CreatePostStatus> {
        return authService.getAuthUserChain<PostsService.CreatePostInput,PostsService.CreatePostStatus>(headers,body)
            .addItem { input, _ ->
                STResponse(postsService.createPost(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }
}