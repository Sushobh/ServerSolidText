package com.sushobh.solidtext.realsimulate

import com.fasterxml.jackson.core.JsonParser
import com.sushobh.com.sushobh.realusertests.client
import com.sushobh.solidtext.apiclasses.AuthServiceInput
import com.sushobh.solidtext.apiclasses.STUser
import com.sushobh.solidtext.auth.debug.AuthDebugService
import com.sushobh.solidtext.realsimulate.actor.user.UserActor
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.springframework.stereotype.Service


@Service
class BigGuy(private val authDebugService: AuthDebugService) {

    @Serializable
    data class ApiResponse<T>(
        @Contextual val error: String? = null,
        val body: T? = null
    )

    @Serializable
    data class SignupResponseBody(val status: String, val stringId: String? = null)
    @Serializable
    data class OtpValidateResponseBody(val status: String)

    @Serializable
    data class LoginResponseBody(val status: String, val tokenText: String?,val user : STUser)

    suspend fun login(email: String, password: String) : UserActor{
        val response: ApiResponse<LoginResponseBody> = postRequest("http://localhost:8080/public/login",
            AuthServiceInput.LoginInput(email, password))
        return UserActor(UserCreds(response.body?.tokenText!!),response.body.user!!)
    }

    suspend fun createUser(email: String, password: String) {

        val response: ApiResponse<SignupResponseBody> = postRequest("http://localhost:8080/public/signup",
            AuthServiceInput.SignupInput(email, password))
        response.body?.stringId?.let { stringIdOfOtp ->
            validateOtpForUser(stringIdOfOtp)
        }
    }

    private suspend fun validateOtpForUser(stringIdOfOtp: String) {
        val otpSent = authDebugService.getOtpByStringId(stringIdOfOtp)
        val response: ApiResponse<OtpValidateResponseBody> = postRequest("http://localhost:8080/public/otpValidate",
            AuthServiceInput.OtpValidateInput(otpSent ?: "", stringIdOfOtp))

    }

    private suspend inline fun <reified T> postRequest(url: String, body: Any): ApiResponse<T> {
        return client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
    }

}