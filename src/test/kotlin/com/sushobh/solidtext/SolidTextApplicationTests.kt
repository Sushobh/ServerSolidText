package com.sushobh.solidtext



import com.sushobh.solidtext.realsimulate.BigGuy
import com.sushobh.solidtext.realsimulate.actor.user.SendFriendReqCommand
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigInteger

@SpringBootTest
class SolidTextApplicationTests {

    @Autowired
    lateinit var bigGuy: BigGuy


    @Test
    fun sendFriendReq() = runBlocking{
        val actor = bigGuy.login("sad258@gmail.com","1234")
        actor.runCommand(SendFriendReqCommand(payload = BigInteger("70")))
        Unit
    }

}
