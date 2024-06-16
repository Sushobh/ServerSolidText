package com.sushobh.solidtext.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "user")
class User {
    @Id
    lateinit var name : String
}