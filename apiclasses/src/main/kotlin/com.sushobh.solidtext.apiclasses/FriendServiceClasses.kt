package com.sushobh.solidtext.apiclasses

import java.math.BigInteger
import java.time.Instant

class FriendServiceClasses {
    data class FrenReqActionInput(val toUserId: BigInteger, val requestId : BigInteger? = null,val action: String)
    sealed class FrenReqActionResult(val status: String?) {
        data class RequestSent(val message: String? = null) : FrenReqActionResult(RequestSent::class.simpleName)
        data object FriendAdded : FrenReqActionResult(FriendAdded::class.simpleName)
        data class Failed(val message: String) : FrenReqActionResult(Failed::class.simpleName)
        data class Refused(val message : String) : FrenReqActionResult(Refused::class.simpleName)
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


sealed class FrenReqAction {
    object Send : FrenReqAction()
    object Refuse : FrenReqAction()
    object Accept : FrenReqAction()
    object Nothing : FrenReqAction()
}

interface STFrenRequest {
      val sentTime : Instant
      val receiverId : BigInteger
      val senderId : BigInteger

}

interface STSentFrenRequest  {
    val req : STFrenRequest?
    val receiverUser : STUser?
}

interface STReceivedFrenRequest  {
    val senderUser : STUser?
    val req : STFrenRequest?
}