package com.sushobh.solidtext.posts.repos

import com.sushobh.solidtext.posts.entity.ETPost
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

internal interface ETPostRepository : CrudRepository<ETPost,BigInteger>{

    @Query("select *from getpostfeed4(?1,?2)", nativeQuery = true)
    fun getPostFeed(forUser : BigInteger,howMany : Int) : List<PJPostFeedItem>

}