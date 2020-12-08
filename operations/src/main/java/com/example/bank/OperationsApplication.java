package com.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "com.example.bank")
@RestController
public class OperationsApplication {

	private final CardsService cardsService;

	public OperationsApplication(CardsService cardsService) {
		this.cardsService = cardsService;
	}

	@GetMapping("/")
	public String getTestMessage() {
		return this.cardsService.message();
	}

	public static void main(String[] args) {
		SpringApplication.run(OperationsApplication.class, args);
	}

}
