package com.example.bank;

import com.example.bank.statements.StatementsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = StatementsApplication.class)
class StatementsApplicationTests {

	@Test
	void contextLoads() {
	}

}
