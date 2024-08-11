package com.sushobh.solidtext.apiclasses

import java.math.BigInteger

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
}