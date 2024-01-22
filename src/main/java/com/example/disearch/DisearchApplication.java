package com.example.disearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DisearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisearchApplication.class, args);
	}

}

