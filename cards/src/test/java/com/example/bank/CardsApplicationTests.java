package com.example.bank;

import com.example.bank.cards.CardsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CardsApplication.class)
class CardsApplicationTests {

	@Test
	void contextLoads() {
	}

}
