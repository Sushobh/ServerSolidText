package com.sushobh.solidtext.posts.repos

import com.sushobh.solidtext.posts.entity.ETPost
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface ETPostRepository : CrudRepository<ETPost,BigInteger>{
}