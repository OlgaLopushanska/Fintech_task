package com.fintech.task;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot REST API for Currency rates",
				description = "Spring Boot REST API for Currency rates from Privat bank, Mono bank and Minfin",
				contact = @Contact(
						name = "Olga Lopushanska",
						email = "olhachirva@gmail.com"
				)
		)
)
public class ApiForFintechTaskApplication {

	@Bean
	public WebClient webClient() {
		return WebClient.builder().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiForFintechTaskApplication.class, args);
	}

}
