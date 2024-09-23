package com.sushobh.solidtext.friends

import com.sushobh.solidtext.apiclasses.FrenReqAction
import com.sushobh.solidtext.apiclasses.FrenReqStatus
import com.sushobh.solidtext.apiclasses.FrenReqStatus.Accepted
import com.sushobh.solidtext.apiclasses.FrenReqStatus.Nothing
import com.sushobh.solidtext.apiclasses.FrenReqStatus.Sent

 internal fun String.toFrenReqAction() : FrenReqAction {
    if(this == "Send"){
        return FrenReqAction.Send()
    }
    else if(this == "Refuse"){
        return FrenReqAction.Refuse()
    }
    else if(this == "Accept"){
        return FrenReqAction.Accept()
    }
    else if(this == "Cancel"){
        return FrenReqAction.CancelRequest()
    }
    return FrenReqAction.Nothing()
}


internal fun String.frenReqStatusFromText(): FrenReqStatus {
    return when (this) {
        Sent().name -> Sent()
        Accepted().name -> Accepted()
        FrenReqStatus.Refused().name -> FrenReqStatus.Refused()
        else -> Nothing()
    }
}