package com.sushobh.solidtext.apiclasses

import java.math.BigInteger

class AuthServiceClasses {
    data class SearchUserInput(val userName : String)
    data class LoginInput(val email: String, val password: String)
    data class SignupInput(val email: String, val password: String)
    data class OtpValidateInput(val otpText: String, val otpId : String)
    data class UpdateUserNameInput(val newName : String)
    data class UserPropInput(val key : String, val value : String? = null)
    sealed class SignupStatus(val status : String?)  {
        data class OtpSent(val stringId : String) : SignupStatus(OtpSent::class.simpleName)
        data object UserAlreadyExists : SignupStatus(UserAlreadyExists::class.simpleName)
        data object Error : SignupStatus(kotlin.Error::class.simpleName)
    }

    sealed class OtpValidateStatus(val status: String?) {
        data object Success : OtpValidateStatus(Success::class.simpleName)
        data object ExpiredRequest : OtpValidateStatus(ExpiredRequest::class.simpleName)
        data object InvalidDetails : OtpValidateStatus(InvalidDetails::class.simpleName)
    }

    sealed class LoginStatus(val status : String?) {
        data object InvalidCredentials : LoginStatus(InvalidCredentials::class.simpleName)
        data class Success(val tokenText: String) : LoginStatus(Success::class.simpleName)
    }

    sealed class UpdateUserNameStatus(val status : String?) {
        data class Success(val respUser: RespETUser) : UpdateUserNameStatus(Success::class.simpleName)
        data object Failed : UpdateUserNameStatus(Success::class.simpleName)
    }

    sealed class SearchUserStatus(val status : String?) {
        data class Found(val user: STUser)  : SearchUserStatus(Found::class.simpleName)
        object UserNotFound : SearchUserStatus(UserNotFound::class.simpleName)
        object Failed : SearchUserStatus(Failed::class.simpleName)
    }

    sealed class UpdateUserPropStatus(val status : String?) {
        data object Success : UpdateUserPropStatus(Success::class.simpleName)
        data class Failed(val message : String? = null) : UpdateUserPropStatus(Failed::class.simpleName)
    }

}

data class STUser(val userId : BigInteger,
                  val userName : String? = null,
                  val profilePic1  : String? = null,
                  val fullName : String? = null
                 )

data class RespETUser(val emailId : String, val userName : String, val userId : BigInteger)