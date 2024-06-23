package com.sushobh.auth.repository

import com.sushobh.auth.entity.SignupAttempt
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface SignupAttemptRepo : CrudRepository<SignupAttempt,BigInteger>{

}