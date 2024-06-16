package com.sushobh.solidtext.repository

import com.sushobh.solidtext.entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User,String> {
}