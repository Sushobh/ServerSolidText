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
        bigGuy.createUser("sad261@gmail.com","1234")
        val actor = bigGuy.login("sad261@gmail.com","1234")
        actor.runCommand(SendFriendReqCommand(payload = BigInteger("70")))
        Unit
    }

    @Test
    fun sendPlenty() = runBlocking {
        (300..310).forEach {
            bigGuy.createUser("sad${it}@gmail.com","1234")
            val actor = bigGuy.login("sad${it}@gmail.com","1234")
            actor.runCommand(SendFriendReqCommand(payload = BigInteger("70")))
            Unit
        }
    }

    @Test
    fun sendPlentToPlenty() = runBlocking {
        val actor = bigGuy.login("sushobh5@gmail.com","1234")
        (331..340).forEach {
            bigGuy.createUser("sad${it}@gmail.com","1234")
            actor.runCommand(SendFriendReqCommand(payload =
             BigInteger(bigGuy.login("sad${it}@gmail.com","1234").userInfo.userId.toString())))
            Unit
        }
    }

}
