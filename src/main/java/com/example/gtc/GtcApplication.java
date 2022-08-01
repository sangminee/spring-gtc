package com.example.gtc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GtcApplication {

	public static void main(String[] args) {
		SpringApplication.run(GtcApplication.class, args);
	}

}
