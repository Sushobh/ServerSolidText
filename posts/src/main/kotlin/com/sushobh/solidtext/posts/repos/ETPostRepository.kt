package com.sushobh.solidtext.posts.repos

import com.sushobh.solidtext.posts.entity.ETPost
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

internal interface ETPostRepository : CrudRepository<ETPost,BigInteger>{

    @Query("select *from getpostfeed4(?1,?2)", nativeQuery = true)
    fun getPostFeed(forUser : BigInteger,howMany : Int) : List<PJPostFeedItem>


    @Query("select id,post.by_user_id as byUser,post.time,post_text as postText,post.status,time as addedtime from post where by_user_id = ?1 order by id desc limit ?2", nativeQuery = true)
    fun getUserPosts(forUser : BigInteger, limit : Int) : List<PJPostFeedItem>

    @Query("select id,post.by_user_id as byUser,post.time,post_text as postText,post.status,time as addedtime from post where by_user_id = ?1 and id < ?2 order by id desc limit ?3", nativeQuery = true)
    fun getUserPostsAfterId(forUser : BigInteger, afterId : BigInteger, limit : Int) : List<PJPostFeedItem>

}