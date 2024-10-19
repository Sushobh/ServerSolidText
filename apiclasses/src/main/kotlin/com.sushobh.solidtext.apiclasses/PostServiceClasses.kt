@file:UseSerializers(InstantSerializer::class,BigIntegerSerializer::class)
package com.sushobh.solidtext.apiclasses

import com.sushobh.solidtext.apiclasses.client.serializers.BigIntegerSerializer
import com.sushobh.solidtext.apiclasses.client.serializers.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigInteger
import java.time.Instant
import java.time.OffsetDateTime

class PostServiceClasses {

    @Serializable
    sealed class CreatePostStatus(val status : String?) {
         @Serializable
         data class Success(val message : String? = null) : CreatePostStatus(Success::class.simpleName)
         @Serializable
         data class  Failed(val reason : String) : CreatePostStatus(Failed::class.simpleName)
    }
    @Serializable
    data class CreatePostInput(val text : String)
    @Serializable
    data class PostLikeInput(val postId : BigInteger, val isLike : Boolean)

    @Serializable
    sealed class PostLikeStatus(val status : String?) {
        @Serializable
        data class Success(val message : String? = null) : PostLikeStatus(Success::class.simpleName)
        @Serializable
        data class  Failed(val message : String? = null) : PostLikeStatus(Failed::class.simpleName)
    }

    @Serializable
    sealed class GetUserPostsStatus(val status : String?){
        @Serializable
        data class Success(val userPosts : List<STPost>) : GetUserPostsStatus(Success::class.simpleName)
        @Serializable
        data class Failed(val message : String? = null) : GetUserPostsStatus(Failed::class.simpleName)
    }

    @Serializable
    sealed class PostFeedStatus(val status : String?){
        @Serializable
        data class Success(val userPosts : List<STPost>) : PostFeedStatus(Success::class.simpleName)
        @Serializable
        data class Failed(val message : String? = null) : PostFeedStatus(Failed::class.simpleName)
    }


    @Serializable
    class STPost ( val id : BigInteger,
                  val byUser : STUser?,
                 val addedTime : Instant,
                  val postText : String,
                  val status : String) {

    }

}


