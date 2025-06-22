package com.demo.univer;

import com.demo.univer.config.TestcontainersConfiguration;
import org.springframework.boot.SpringApplication;

public class TestUniverDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(UniverDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}
}
