package com.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OperationsApplication {

	// Commands to run zookeeper and kafka:
	// - zkserver (any console)
	// - .\bin\windows\kafka-server-start.bat .\config\server.properties
	public static void main(String[] args) {
		SpringApplication.run(OperationsApplication.class, args);
	}

}
