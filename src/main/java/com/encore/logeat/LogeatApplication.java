package com.encore.logeat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogeatApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogeatApplication.class, args);
	}

}
