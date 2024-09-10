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



sealed class SentFriendRequestListStatus(var status : String) {
    data object Error : SentFriendRequestListStatus(Error::class.simpleName!!)
    data class Success(val reqs : List<ISTSentFrenRequest>) : SentFriendRequestListStatus(Success::class.simpleName!!)
}

sealed class ReceivedFriendRequestListStatus(var status : String) {
    data object Error : SentFriendRequestListStatus(Error::class.simpleName!!)
    data class Success(val reqs : List<ISTReceivedFrenRequest>) : ReceivedFriendRequestListStatus(Success::class.simpleName!!)
}


interface ISTFrenRequest {
      val sentTime : Instant
      val receiverId : BigInteger
      val senderId : BigInteger

}


interface ISTSentFrenRequest  {
    val req : ISTFrenRequest?
    val receiverUser : STUser?
}

interface ISTReceivedFrenRequest  {
    val senderUser : STUser?
    val req : ISTFrenRequest?
}