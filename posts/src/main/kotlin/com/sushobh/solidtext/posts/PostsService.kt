package com.sushobh.solidtext.posts

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.auth.api.AuthService
import com.sushobh.solidtext.auth.api.STUser
import com.sushobh.solidtext.posts.api.STPost
import com.sushobh.solidtext.posts.entity.ETPost
import com.sushobh.solidtext.posts.entity.ETPostLike
import com.sushobh.solidtext.posts.entity.ETPostLikeId
import com.sushobh.solidtext.posts.repos.ETPostLikeRepo
import com.sushobh.solidtext.posts.repos.ETPostRepository
import com.sushobh.solidtext.posts.repos.PJPostFeedItem
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.time.Instant


@Service
internal class PostsService(private val dateUtil: DateUtil,
                            private val etPostRepository: ETPostRepository,
                            private val authService: AuthService,
    private val etPostLikeRepo : ETPostLikeRepo
    ) {

    sealed class CreatePostStatus(val status : String?) {
         data object Success : CreatePostStatus(Success::class.simpleName)
         data class  Failed(val reason : String) : CreatePostStatus(Failed::class.simpleName)
    }

    data class CreatePostInput(val text : String)

    data class PostLikeInput(val postId : BigInteger, val isLike : Boolean)

    sealed class PostLikeStatus(val status : String?) {
        data object Success : PostLikeStatus(Success::class.simpleName)
        data object  Failed : PostLikeStatus(Failed::class.simpleName)
    }

    fun createPost(body: CreatePostInput, user: STUser): CreatePostStatus {
         val postText = body.text
         if(postText.length > POST_TEXT_MAX_LENGTH){
             return CreatePostStatus.Failed("Post exceeds $POST_TEXT_MAX_LENGTH characters")
         }
         val etPost = ETPost(postText = postText,status = "",time = dateUtil.getCurrentTime(), byUserId = user.userId)
         etPostRepository.save(etPost)
         return CreatePostStatus.Success
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

    suspend fun postLikeInput(postLikeInput: PostLikeInput,user: STUser) : PostLikeStatus{
        try {
            if(postLikeInput.isLike){
                etPostLikeRepo.save(ETPostLike(dateUtil.getCurrentTime(), postId = postLikeInput.postId, userId = user.userId))
            }
            else {
               etPostLikeRepo.deleteById(ETPostLikeId(userId = user.userId, postId = postLikeInput.postId))
            }
            return PostLikeStatus.Success
        } catch (e: Exception) {
           return PostLikeStatus.Failed
        }

    }

}