@file:UseSerializers(InstantSerializer::class,BigIntegerSerializer::class)

package com.sushobh.solidtext.apiclasses

import com.sushobh.solidtext.apiclasses.client.serializers.BigIntegerSerializer
import com.sushobh.solidtext.apiclasses.client.serializers.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigInteger


class AuthServiceInput {
    data class SearchUserInput(val userName : String)
    @Serializable
    data class LoginInput(val email: String, val password: String)
    @Serializable
    data class SignupInput(val email: String, val password: String)
    @Serializable
    data class OtpValidateInput(val otpText: String, val otpId : String)
    @Serializable
    data class UpdateUserNameInput(val newName : String)
    @Serializable
    data class UserPropInput(val key : String, val value : String? = null)

}

class AuthServiceOutput {

    @Serializable
    sealed class SignupStatus(val status : String?)  {
        @Serializable
        data class OtpSent(val stringId : String) : SignupStatus(OtpSent::class.simpleName)
        @Serializable
        data class UserAlreadyExists(val message : String? = null) : SignupStatus(UserAlreadyExists::class.simpleName)
        @Serializable
        data class Error(val message : String? = null) : SignupStatus(kotlin.Error::class.simpleName)
    }
    @Serializable
    sealed class OtpValidateStatus(val status: String?) {
        @Serializable
        data class Success(val message : String? = null) : OtpValidateStatus(Success::class.simpleName)
        @Serializable
        data class ExpiredRequest(val message : String? = null) : OtpValidateStatus(ExpiredRequest::class.simpleName)
        @Serializable
        data class InvalidDetails(val message : String? = null) : OtpValidateStatus(InvalidDetails::class.simpleName)
    }
    @Serializable
    sealed class LoginStatus(val status : String?) {
        @Serializable
        data class InvalidCredentials(val message : String? = null) : LoginStatus(InvalidCredentials::class.simpleName)
        @Serializable
        data class Success(val tokenText: String) : LoginStatus(Success::class.simpleName)
    }
    @Serializable
    sealed class UpdateUserNameStatus(val status : String?) {
        @Serializable
        data class Success(val respUser: RespETUser) : UpdateUserNameStatus(Success::class.simpleName)
        @Serializable
        data class Failed(val message : String? = null) : UpdateUserNameStatus(Success::class.simpleName)
    }
    @Serializable
    sealed class SearchUserStatus(val status : String?) {
        data class Found(val user: STUser)  : SearchUserStatus(Found::class.simpleName)
        data class UserNotFound(val message : String? = null) : SearchUserStatus(UserNotFound::class.simpleName)
        data class Failed(val message : String? = null) : SearchUserStatus(Failed::class.simpleName)
    }

    @Serializable
    sealed class UpdateUserPropStatus(val status : String?) {
        @Serializable
        data class Success(val user: STUser?) : UpdateUserPropStatus(Success::class.simpleName)
        @Serializable
        data class Failed(val message : String? = null) : UpdateUserPropStatus(Failed::class.simpleName)
    }
    @Serializable
    sealed class GetUserPropsStatus(val status : String?) {
        @Serializable
        data class Success(val userProps : List<UserProp>) : GetUserPropsStatus(Success::class.simpleName)
        @Serializable
        data class Failed(val message : String? = null) : GetUserPropsStatus(Failed::class.simpleName)
    }


}


@Serializable
data class UserProp(val key: String,val value: String?,val isEditableOnPhone : Boolean)

@Serializable
data class STUser( val userId : BigInteger,
                  val userName : String? = null,
                  val profilePic1  : String? = null,
                  val fullName : String? = null
                 )

@Serializable
data class RespETUser(val emailId : String, val userName : String, val userId : BigInteger)