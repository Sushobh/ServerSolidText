package com.sushobh.solidtext.apiclasses

import java.math.BigInteger
import java.time.Instant

class FriendServiceClasses {
    data class FrenReqActionInput(val toUserId: BigInteger, val action: String)
    sealed class FrenReqResult(val status: String?) {
        data class RequestSent(val message: String? = null) : FrenReqResult(RequestSent::class.simpleName)
        data object FriendAdded : FrenReqResult(FriendAdded::class.simpleName)
        data class Failed(val message: String) : FrenReqResult(Failed::class.simpleName)
    }

    data class FrenSearchUserByNameInput(val userName: String)
    data class FrenReqSendingAbility(val canSend: Boolean)
    sealed class FrenSearchStatus(val status: String?) {
        data object NotFound : FrenSearchStatus(NotFound::class.simpleName)
        data class Found(val reqSendingAbility: FrenReqSendingAbility, val user : STUser) : FrenSearchStatus(Found::class.simpleName)
    }
}

sealed class FrenReqStatus(val name : String?) {
    data object Accepted : FrenReqStatus(Accepted::class.simpleName)
    data object Refused : FrenReqStatus(Refused::class.simpleName)
    data object Sent : FrenReqStatus(Sent::class.simpleName)
    data object InActive : FrenReqStatus(InActive::class.simpleName)
    data object Nothing : FrenReqStatus("")
}

interface STFrenRequest {
      val senttime : Instant
      val receiverId : BigInteger
      val userName : String
      val senderId : BigInteger
}