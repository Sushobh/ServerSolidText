package com.sushobh.solidtext.apiclasses

import com.sushobh.solidtext.apiclasses.client.serializers.BigIntegerSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigInteger
import java.time.Instant
import java.time.OffsetDateTime

class PostServiceClasses {

    @Serializable
    sealed class CreatePostStatus(val status : String?) {
         @Serializable
         data object Success : CreatePostStatus(Success::class.simpleName)
         @Serializable
         data class  Failed(val reason : String) : CreatePostStatus(Failed::class.simpleName)
    }
    @Serializable
    data class CreatePostInput(val text : String)
    @Serializable
    data class PostLikeInput(@Serializable(with = BigIntegerSerializer::class) val postId : BigInteger, val isLike : Boolean)

    @Serializable
    sealed class PostLikeStatus(val status : String?) {
        @Serializable
        data object Success : PostLikeStatus(Success::class.simpleName)
        @Serializable
        data object  Failed : PostLikeStatus(Failed::class.simpleName)
    }

    @Serializable
    sealed class GetUserPostsStatus(val status : String?){
        @Serializable
        data class Success(val userPosts : List<STPost>) : GetUserPostsStatus(Success::class.simpleName)
        @Serializable
        data class Failed(val message : String? = null) : GetUserPostsStatus(Failed::class.simpleName)
    }


    @Serializable
    data class RespETPost(val text : String,@Contextual val time : OffsetDateTime, val byUser : RespETUser)
    @Serializable
    class STPost (@Serializable(with = BigIntegerSerializer::class) val id : BigInteger,
                  val byUser : STUser?,
                  @Contextual val addedTime : Instant,
                  val postText : String,
                  val status : String) {

    }

}


