@file:UseSerializers(InstantSerializer::class,BigIntegerSerializer::class)

package com.sushobh.solidtext.apiclasses

import com.sushobh.solidtext.apiclasses.client.serializers.BigIntegerSerializer
import com.sushobh.solidtext.apiclasses.client.serializers.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigInteger
import java.time.Instant

class FriendServiceClasses {
    @Serializable
    data class FrenReqActionInput( val toUserId: BigInteger,
                                   val requestId : BigInteger? = null, val action: String)

    @Serializable
    sealed class FrenReqActionResult(val status: String?) {

        @Serializable
        data class RequestSent(val message: String? = null) : FrenReqActionResult(RequestSent::class.simpleName)


        @Serializable
        data class FriendAdded(val message : String? = null) : FrenReqActionResult(FriendAdded::class.simpleName)


        @Serializable
        data class Failed(val message: String) : FrenReqActionResult(Failed::class.simpleName)


        @Serializable
        data class Refused(val message : String) : FrenReqActionResult(Refused::class.simpleName)

        @SerialName("Cancelled")
        @Serializable
        data class Cancelled(val message : String? = null) : FrenReqActionResult(Cancelled::class.simpleName)
    }

    @Serializable
    data class FrenSearchUserByNameInput(val userName: String)

    @Serializable
    data class FrenReqSendingAbility(val canSend: Boolean)

    @Serializable
    sealed class FrenSearchStatus(val status: String?) {
        @Serializable
        data class NotFound(val message : String? = null) : FrenSearchStatus(NotFound::class.simpleName)
        @Serializable
        data class Found(val reqSendingAbility: FrenReqSendingAbility, val user : STUser) : FrenSearchStatus(Found::class.simpleName)
    }
}

@Serializable
sealed class FrenReqStatus(val name : String? = null) {
    @Serializable
    data class Accepted(val message : String? = null) : FrenReqStatus(Accepted::class.simpleName)
    @Serializable
    data class Refused(val message : String? = null) : FrenReqStatus(Refused::class.simpleName)
    @Serializable
    data class Sent(val message : String? = null) : FrenReqStatus(Sent::class.simpleName)
    @Serializable
    data class InActive(val message : String? = null) : FrenReqStatus(InActive::class.simpleName)
    @Serializable
    data class Nothing(val message : String? = null) : FrenReqStatus("")
}


sealed class FrenReqAction {
    @Serializable
    data class Send(val message : String? = null) : FrenReqAction()
    @Serializable
    data class Refuse(val message : String? = null) : FrenReqAction()
    @Serializable
    data class Accept(val message : String? = null) : FrenReqAction()
    @Serializable
    data class CancelRequest(val message : String? = null) : FrenReqAction()
    @Serializable
    data class Nothing(val message : String? = null) : FrenReqAction()
}


@Serializable
sealed class SentFriendRequestListStatus(var status : String) {
    @Serializable
    data class Error(val message : String? = null) : SentFriendRequestListStatus(Error::class.simpleName!!)
    @Serializable
    data class Success(val reqs : List<ISTSentFrenRequest>) : SentFriendRequestListStatus(Success::class.simpleName!!)
}

@Serializable
sealed class ReceivedFriendRequestListStatus(var status : String) {
    @Serializable
    data class Error(val message : String? = null) : SentFriendRequestListStatus(Error::class.simpleName!!)
    @Serializable
    data class Success(val reqs : List<ISTReceivedFrenRequest>) : ReceivedFriendRequestListStatus(Success::class.simpleName!!)
}


interface ISTFrenRequest {
      val sentTime : Instant
      val receiverId : BigInteger
      val senderId : BigInteger
      val id : BigInteger
}


interface ISTSentFrenRequest  {
    val req : ISTFrenRequest?
    val receiverUser : STUser?
}

interface ISTReceivedFrenRequest  {
    val senderUser : STUser?
    val req : ISTFrenRequest?
}