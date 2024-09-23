package com.sushobh.solidtext.realsimulate.actor.user

import com.sushobh.solidtext.realsimulate.actor.Command
import java.math.BigInteger

class SendFriendReqCommand(override val key: String = "SendFriendReqCommand", override val payload: BigInteger) : Command<BigInteger> {

}