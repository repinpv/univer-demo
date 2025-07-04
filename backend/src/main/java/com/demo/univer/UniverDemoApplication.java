package com.demo.univer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class UniverDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniverDemoApplication.class, args);
	}

}
