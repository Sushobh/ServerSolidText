package com.sushobh.solidtext.auth.service

import com.sushobh.solidtext.auth.entity.ETUser
import common.util.requests.STRequest
import org.springframework.web.bind.annotation.RequestBody

class STUserRequest<X>(requestBody: X,val etUser : ETUser) : STRequest<X>(requestBody) {
}