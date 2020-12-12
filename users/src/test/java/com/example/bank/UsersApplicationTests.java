package com.example.bank;

import com.example.bank.users.UsersApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = UsersApplication.class)
class UsersApplicationTests {

	@Test
	void contextLoads() {
	}

}
