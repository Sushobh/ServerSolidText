package com.sushobh

import kotlinx.coroutines.runBlocking

fun main() {
    val bigGuy = BigGuy()
    runBlocking {
        bigGuy.createUser("sushobh449@gmail.com","1234")
    }
}