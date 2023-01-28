package com.hhgg.hhggbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HHggBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HHggBeApplication.class, args);
	}

}
