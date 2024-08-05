package com.sushobh.solidtext.posts.repos

import com.sushobh.solidtext.posts.entity.ETPostLike
import com.sushobh.solidtext.posts.entity.ETPostLikeId
import org.springframework.data.repository.CrudRepository

interface ETPostLikeRepo : CrudRepository<ETPostLike,ETPostLikeId> {
}