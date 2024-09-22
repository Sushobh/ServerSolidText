package com.sushobh.solidtext.realsimulate

import com.sushobh.com.sushobh.realusertests.client
import com.sushobh.solidtext.apiclasses.AuthServiceInput
import com.sushobh.solidtext.auth.debug.AuthDebugService
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
    data class SignupApiResponse(@Contextual val error: String? = null, val body: SignupApi_ResponseBody? = null)
    @Serializable
    data class SignupApi_ResponseBody(val status: String, val stringId: String? = null)

    @Serializable
    private data class OtpValidate_Response(@Contextual val error: String? = null, val body: OtpValidate_ResponseBody? = null)
    @Serializable
    private data class OtpValidate_ResponseBody(val status: String)

    suspend fun login(email : String,password : String) {
        val response: HttpResponse = client.post("http://localhost:8080/public/login") {
            contentType(ContentType.Application.Json)
            setBody(AuthServiceInput.LoginInput(email,password))
        }
        println(response.bodyAsText())
    }

    suspend fun createUser(email : String,password: String){
        val response : SignupApiResponse = client.post("http://localhost:8080/public/signup") {
            contentType(ContentType.Application.Json)
            setBody(AuthServiceInput.SignupInput(email,password))
        }.body()

        response.body?.stringId?.let { stringIdOfOtp ->
           validateOtpForUser(stringIdOfOtp)
        }
    }

    suspend fun validateOtpForUser(stringIdOfOtp : String) {
        val otpSent = authDebugService.getOtpByStringId(stringIdOfOtp)
        val otpvalidateResponse : OtpValidate_Response = client.post("http://localhost:8080/public/otpValidate") {
            contentType(ContentType.Application.Json)
            setBody(AuthServiceInput.OtpValidateInput(otpSent ?: "",stringIdOfOtp))
        }.body()
    }

}