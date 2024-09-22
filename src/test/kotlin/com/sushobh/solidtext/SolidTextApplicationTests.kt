package com.sushobh.solidtext



import com.sushobh.solidtext.realsimulate.BigGuy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SolidTextApplicationTests {

    @Autowired
    lateinit var bigGuy: BigGuy


    @Test
    fun hello() = runBlocking{
        bigGuy.createUser("sad255@gmail.com","asdasd")
    }

}
