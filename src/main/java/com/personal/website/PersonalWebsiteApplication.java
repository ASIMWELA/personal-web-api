package com.personal.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PersonalWebsiteApplication
{
	public static void main(String[] args) {
		SpringApplication.run(PersonalWebsiteApplication.class, args);
	}
}
