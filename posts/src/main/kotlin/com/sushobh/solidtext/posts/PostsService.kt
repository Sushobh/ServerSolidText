package com.sushobh.solidtext.posts

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.auth.api.STUser
import com.sushobh.solidtext.posts.entity.ETPost
import com.sushobh.solidtext.posts.repos.ETPostRepository
import org.springframework.stereotype.Service


@Service
class PostsService(private val dateUtil: DateUtil,private val etPostRepository: ETPostRepository) {


    sealed class CreatePostStatus(val status : String?) {
         data object Success : CreatePostStatus(Success::class.simpleName)
         data class  Failed(val reason : String) : CreatePostStatus(Failed::class.simpleName)
    }

    data class CreatePostInput(val text : String)

    fun createPost(body: CreatePostInput, user: STUser): PostsService.CreatePostStatus {
         val postText = body.text
         if(postText.length > POST_TEXT_MAX_LENGTH){
             return CreatePostStatus.Failed("Post exceeds $POST_TEXT_MAX_LENGTH characters")
         }
         val etPost = ETPost(postText = postText,status = "",time = dateUtil.getCurrentTime(), byUserId = user.userId)
         etPostRepository.save(etPost)
         return CreatePostStatus.Success
    }

}