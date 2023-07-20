package com.devoceanyoung.greendev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GreendevApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreendevApplication.class, args);
	}

}
