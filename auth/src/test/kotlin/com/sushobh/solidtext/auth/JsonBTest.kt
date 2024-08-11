package com.sushobh.solidtext.auth

import com.sushobh.solidtext.auth.repository.ETUserRepo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import java.math.BigInteger

@SpringBootTest
class JsonBTest {


    @Autowired
    private lateinit var etUserRepo : ETUserRepo

    @Test
    fun hello(){
         val user = etUserRepo.findById(BigInteger("70"))
         Assertions.assertNotNull(user)

    }

}