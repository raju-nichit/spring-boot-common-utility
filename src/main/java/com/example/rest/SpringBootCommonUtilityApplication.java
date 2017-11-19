package com.example.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringBootCommonUtilityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCommonUtilityApplication.class, args);
	}
}
