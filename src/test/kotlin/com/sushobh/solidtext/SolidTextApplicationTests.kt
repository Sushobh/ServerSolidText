package com.sushobh.solidtext

import com.sushobh.auth.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SolidTextApplicationTests {

	@Autowired
	lateinit var userService : UserService

	@Test
	fun contextLoads() {
		toString()
	}

}
