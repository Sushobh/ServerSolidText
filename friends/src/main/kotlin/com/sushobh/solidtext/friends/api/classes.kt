package com.sushobh.solidtext.com.sushobh.solidtext.friends.api

import com.sushobh.solidtext.auth.api.STUser
import com.sushobh.solidtext.com.sushobh.solidtext.friends.api.FrenReqStatus.Accepted
import com.sushobh.solidtext.com.sushobh.solidtext.friends.api.FrenReqStatus.Nothing
import com.sushobh.solidtext.com.sushobh.solidtext.friends.api.FrenReqStatus.Sent

data class FrenReq(val fromUser : STUser)



sealed class FrenReqStatus(val name : String?) {
    data object Accepted : FrenReqStatus(Accepted::class.simpleName)
    data object Refused : FrenReqStatus(Refused::class.simpleName)
    data object Sent : FrenReqStatus(Sent::class.simpleName)
    data object InActive : FrenReqStatus(InActive::class.simpleName)
    data object Nothing : FrenReqStatus("")
}


