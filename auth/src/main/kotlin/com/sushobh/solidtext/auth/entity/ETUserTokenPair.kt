package com.sushobh.solidtext.auth.entity

import jakarta.persistence.*
import java.math.BigInteger


@Entity
@Table(name = "user_token_pair")
internal class ETUserTokenPair() {

    constructor(etUserTokenPairId: ETUserTokenPairId) : this() {
        this.id = etUserTokenPairId
    }

    @EmbeddedId
    lateinit var id: ETUserTokenPairId
        private set
}

@Embeddable
class ETUserTokenPairId() {

    constructor(userId: BigInteger, tokenId: BigInteger) : this() {
        this.userId = userId
        this.tokenId = tokenId
    }

    @Column(name = "userid")
    lateinit var userId: BigInteger
        private set

    @Column(name = "tokenid")
    lateinit var tokenId: BigInteger
        private set
}