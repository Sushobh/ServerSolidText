package com.sushobh

import com.sushobh.solidtext.apiclasses.AuthServiceInput
import com.sushobh.solidtext.com.sushobh.solidtext.friends.FriendsService
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class BigGuy(private val friendService : FriendsService) {


    suspend fun login(email : String,password : String) {
        val response: HttpResponse = client.post("http://localhost:8080/public/login") {
            contentType(ContentType.Application.Json)
            setBody(AuthServiceInput.LoginInput(email,password))
        }
        println(response.bodyAsText())
    }

    suspend fun createUser(email : String,password: String){
        val response: HttpResponse = client.post("http://localhost:8080/public/signup") {
            contentType(ContentType.Application.Json)
            setBody(AuthServiceInput.SignupInput(email,password))
        }
        println(response.status)
        println(response.bodyAsText())
    }

}