package com.sushobh.solidtext.apiclasses

import java.math.BigInteger
import java.time.Instant
import java.time.OffsetDateTime

class PostServiceClasses {
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


    sealed class GetUserPostsStatus(val status : String?){
        data class Success(val userPosts : List<STPost>) : GetUserPostsStatus(Success::class.simpleName)
        data class Failed(val message : String? = null) : GetUserPostsStatus(Failed::class.simpleName)
    }


    data class RespETPost(val text : String, val time : OffsetDateTime, val byUser : RespETUser)
    class STPost (val id : BigInteger,
                  val byUser : STUser?,
                  val addedTime : Instant,
                  val postText : String,
                  val status : String) {

    }

}


