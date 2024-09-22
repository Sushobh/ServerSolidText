package com.sushobh.solidtext.apiclasses.client

private data class SignupApiResponse(val error: Any? = null, val body: SignupResponseBody? = null)
private data class SignupResponseBody(val status: String, val stringId: String?)