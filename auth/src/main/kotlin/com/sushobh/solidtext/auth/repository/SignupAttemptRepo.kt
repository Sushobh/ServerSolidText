package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETSignupAttempt
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface SignupAttemptRepo : CrudRepository<ETSignupAttempt,BigInteger>{
     @Query("select * from signup_attempt  where email = ?1 order by time desc LIMIT 1", nativeQuery = true)
     fun findLatestByEmail(email : String) : ETSignupAttempt?
}