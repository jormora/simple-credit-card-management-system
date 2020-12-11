package com.example.bank;

import com.example.bank.operations.OperationsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = OperationsApplication.class)
class OperationsApplicationTests {

	@Test
	void contextLoads() {
	}

}
