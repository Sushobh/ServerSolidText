package com.sushobh.solidtext.realsimulate.actor.user

import com.sushobh.com.sushobh.realusertests.client
import com.sushobh.solidtext.apiclasses.AuthServiceInput
import com.sushobh.solidtext.apiclasses.FriendServiceClasses
import com.sushobh.solidtext.apiclasses.STUser
import com.sushobh.solidtext.realsimulate.BASE_URL
import com.sushobh.solidtext.realsimulate.BigGuy.ApiResponse
import com.sushobh.solidtext.realsimulate.BigGuy.LoginResponseBody
import com.sushobh.solidtext.realsimulate.UserCreds
import com.sushobh.solidtext.realsimulate.actor.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.math.BigInteger

class UserActor(val creds: UserCreds,val userInfo : STUser) : Actor {



    override suspend fun runCommand(command: Command<*>): CommandResult {
        when(command){
            is SendFriendReqCommand -> {
                val response: ApiResponse<FriendServiceClasses.FrenReqActionResult> = postRequest("$BASE_URL/frens/sendFrenReq",
                    FriendServiceClasses.FrenReqSendInput(command.payload), mapOf("AUTH_HEADER" to creds.authHeader)
                )
                println(response)
            }
        }
        return FailedCommandResult()
    }

    private suspend inline fun <reified T> postRequest(url: String, body: Any,headersToSend : Map<String,String> = hashMapOf()): ApiResponse<T> {
        return client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(body)
            headersToSend.forEach { t, u ->
                header(t,u)
            }
        }.body()
    }

}