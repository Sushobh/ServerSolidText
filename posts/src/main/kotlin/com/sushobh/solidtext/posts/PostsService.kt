package com.sushobh.solidtext.posts

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.apiclasses.PostServiceClasses
import com.sushobh.solidtext.apiclasses.PostServiceClasses.PostLikeInput
import com.sushobh.solidtext.apiclasses.STUser
import com.sushobh.solidtext.auth.api.AuthService
import com.sushobh.solidtext.posts.api.STPost
import com.sushobh.solidtext.posts.entity.ETPost
import com.sushobh.solidtext.posts.entity.ETPostLike
import com.sushobh.solidtext.posts.entity.ETPostLikeId
import com.sushobh.solidtext.posts.repos.ETPostLikeRepo
import com.sushobh.solidtext.posts.repos.ETPostRepository
import org.springframework.stereotype.Service


@Service
internal class PostsService(private val dateUtil: DateUtil,
                            private val etPostRepository: ETPostRepository,
                            private val authService: AuthService,
    private val etPostLikeRepo : ETPostLikeRepo
    ) {

    fun createPost(body: PostServiceClasses.CreatePostInput, user: STUser): PostServiceClasses.CreatePostStatus {
         val postText = body.text
         if(postText.length > POST_TEXT_MAX_LENGTH){
             return PostServiceClasses.CreatePostStatus.Failed("Post exceeds $POST_TEXT_MAX_LENGTH characters")
         }
         val etPost = ETPost(postText = postText,status = "",time = dateUtil.getCurrentTime(), byUserId = user.userId)
         etPostRepository.save(etPost)
         return PostServiceClasses.CreatePostStatus.Success
    }


    suspend fun getPostFeed(user : STUser) : List<STPost> {
        return etPostRepository.getPostFeed(user.userId, NUM_ITEMS_PER_POST_FEED_CALL).map {
            STPost(
                addedTime = it.addedTime,
                postText = it.postText,
                byUser = authService.getUserByid(it.byUser),
                id = it.id,
                status = it.status
            )
        }
    }

    suspend fun postLikeInput(postLikeInput: PostLikeInput, user: STUser) : PostServiceClasses.PostLikeStatus {
        try {
            if(postLikeInput.isLike){
                etPostLikeRepo.save(ETPostLike(dateUtil.getCurrentTime(), postId = postLikeInput.postId, userId = user.userId))
            }
            else {
               etPostLikeRepo.deleteById(ETPostLikeId(userId = user.userId, postId = postLikeInput.postId))
            }
            return PostServiceClasses.PostLikeStatus.Success
        } catch (e: Exception) {
           return PostServiceClasses.PostLikeStatus.Failed
        }

    }

}