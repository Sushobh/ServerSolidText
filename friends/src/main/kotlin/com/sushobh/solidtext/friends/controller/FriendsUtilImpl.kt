package com.sushobh.solidtext.friends.controller

import com.sushobh.solidtext.auth.api.AuthService
import com.sushobh.solidtext.com.sushobh.solidtext.friends.repos.ETConRepo
import org.springframework.stereotype.Service
import java.math.BigInteger

interface FriendsUtil {
    suspend fun areFriends(userId1 : BigInteger, userId2 : BigInteger) : Boolean
}



@Service
class FriendsUtilImpl (private val etConRepo: ETConRepo) : FriendsUtil {
    override suspend fun areFriends(userId1: BigInteger, userId2: BigInteger) : Boolean {
        val existingFriends = etConRepo.getExistingConnections(userId1, userId2)
        return existingFriends.isNotEmpty()
    }
}