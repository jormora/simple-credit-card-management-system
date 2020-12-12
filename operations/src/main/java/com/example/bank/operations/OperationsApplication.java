package com.example.bank.operations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OperationsApplication {

	// Commands to run zookeeper and kafka on windows:
	// - zkserver (any console)
	// - .\bin\windows\kafka-server-start.bat .\config\server.properties
	public static void main(String[] args) {
		SpringApplication.run(OperationsApplication.class, args);
	}

}
