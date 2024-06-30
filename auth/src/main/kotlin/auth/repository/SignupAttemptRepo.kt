package com.sushobh.auth.repository

import com.sushobh.auth.entity.SignupAttempt
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface SignupAttemptRepo : CrudRepository<SignupAttempt,BigInteger>{
     @Query("select * from signup_attempt  where email = ?1 LIMIT 1", nativeQuery = true)
     fun findByEmail(email : String) : SignupAttempt
}