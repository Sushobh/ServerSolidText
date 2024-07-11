package com.sushobh.solidtext.posts.controllers

import com.sushobh.solidtext.auth.EXTRA_USER
import com.sushobh.solidtext.auth.entity.ETUser
import com.sushobh.solidtext.auth.getUserRequestChain
import com.sushobh.solidtext.auth.service.UserService
import com.sushobh.solidtext.posts.PostsService
import common.util.requests.STResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController


@RestController
class PostsController(private val userService : UserService,private val postsService: PostsService) {

    @PostMapping("/posts/createPost")
    suspend fun createPost(@RequestBody body : PostsService.CreatePostInput,
                           @RequestHeader headers: Map<String, String>
    ): STResponse<PostsService.CreatePostStatus> {
        return getUserRequestChain<PostsService.CreatePostInput, PostsService.CreatePostStatus>(userService,null, headers)
            .addItem { input, _ ->
                STResponse(postsService.createPost(body,input.getExtra<ETUser>(EXTRA_USER).id), null)
            }.next()
    }
}