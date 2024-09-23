package com.sushobh.solidtext.realsimulate.actor

interface Actor {
    suspend fun runCommand(command: Command<*>) : CommandResult
}

abstract class CommandResult() {
   abstract val isSuccess : Boolean
}

class SuccessCommandResult : CommandResult() {
    override val isSuccess: Boolean
        get() = true
}

class FailedCommandResult : CommandResult() {
    override val isSuccess: Boolean
        get() = false
}

interface Command<X> {
    val key : String
    val payload : X
}