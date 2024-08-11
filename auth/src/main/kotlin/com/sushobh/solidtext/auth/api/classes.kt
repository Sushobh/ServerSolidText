package com.sushobh.solidtext.auth.api

import java.math.BigInteger

data class STUser(val userId : BigInteger,
                  val userName : String? = null,
                  val profilePic1  : String? = null
                 )