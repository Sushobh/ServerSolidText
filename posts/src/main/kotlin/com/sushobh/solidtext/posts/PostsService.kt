package com.sushobh.solidtext.posts

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.posts.entity.ETPost
import com.sushobh.solidtext.posts.repos.ETPostRepository
import org.springframework.stereotype.Service
import java.math.BigInteger


@Service
class PostsService(private val dateUtil: DateUtil,private val etPostRepository: ETPostRepository) {


    sealed class CreatePostStatus(val status : String?) {
         data object Success : CreatePostStatus(Success::class.simpleName)
         data class Failed(val reason : String) : CreatePostStatus(Success::class.simpleName)
    }

    data class CreatePostInput(val text : String)

    fun createPost(body: PostsService.CreatePostInput, userId: BigInteger): PostsService.CreatePostStatus {
         val postText = body.text
         val etPost = ETPost(postText = postText,status = "",time = dateUtil.getCurrentTime(), byUserId = userId )
         etPostRepository.save(etPost)
         return CreatePostStatus.Success
    }

}