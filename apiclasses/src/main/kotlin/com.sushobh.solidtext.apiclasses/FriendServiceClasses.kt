package com.sushobh.solidtext.apiclasses

import com.sushobh.solidtext.apiclasses.client.serializers.BigIntegerSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigInteger
import java.time.Instant

class FriendServiceClasses {
    @Serializable
    data class FrenReqActionInput(@Serializable(with = BigIntegerSerializer::class) val toUserId: BigInteger,
                                  @Serializable(with = BigIntegerSerializer::class) val requestId : BigInteger? = null, val action: String)

    @Serializable
    sealed class FrenReqActionResult(val status: String?) {

        @Serializable
        data class RequestSent(val message: String? = null) : FrenReqActionResult(RequestSent::class.simpleName)


        @Serializable
        data object FriendAdded : FrenReqActionResult(FriendAdded::class.simpleName)


        @Serializable
        data class Failed(val message: String) : FrenReqActionResult(Failed::class.simpleName)


        @Serializable
        data class Refused(val message : String) : FrenReqActionResult(Refused::class.simpleName)

        @SerialName("Cancelled")
        @Serializable
        data object Cancelled : FrenReqActionResult(Cancelled::class.simpleName)
    }

    @Serializable
    data class FrenSearchUserByNameInput(val userName: String)

    @Serializable
    data class FrenReqSendingAbility(val canSend: Boolean)

    @Serializable
    sealed class FrenSearchStatus(val status: String?) {
        @Serializable
        data object NotFound : FrenSearchStatus(NotFound::class.simpleName)
        @Serializable
        data class Found(val reqSendingAbility: FrenReqSendingAbility, val user : STUser) : FrenSearchStatus(Found::class.simpleName)
    }
}

@Serializable
sealed class FrenReqStatus(val name : String?) {
    @Serializable
    data object Accepted : FrenReqStatus(Accepted::class.simpleName)
    @Serializable
    data object Refused : FrenReqStatus(Refused::class.simpleName)
    @Serializable
    data object Sent : FrenReqStatus(Sent::class.simpleName)
    @Serializable
    data object InActive : FrenReqStatus(InActive::class.simpleName)
    @Serializable
    data object Nothing : FrenReqStatus("")
}


sealed class FrenReqAction {
    @Serializable
    object Send : FrenReqAction()
    @Serializable
    object Refuse : FrenReqAction()
    @Serializable
    object Accept : FrenReqAction()
    @Serializable
    object CancelRequest : FrenReqAction()
    @Serializable
    object Nothing : FrenReqAction()
}


@Serializable
sealed class SentFriendRequestListStatus(var status : String) {
    @Serializable
    data object Error : SentFriendRequestListStatus(Error::class.simpleName!!)
    @Serializable
    data class Success(val reqs : List<ISTSentFrenRequest>) : SentFriendRequestListStatus(Success::class.simpleName!!)
}

@Serializable
sealed class ReceivedFriendRequestListStatus(var status : String) {
    @Serializable
    data object Error : SentFriendRequestListStatus(Error::class.simpleName!!)
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