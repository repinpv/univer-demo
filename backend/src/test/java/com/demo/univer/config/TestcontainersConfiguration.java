package com.demo.univer.config;

import lombok.SneakyThrows;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

//	private static final AtomicInteger grpcPort = new AtomicInteger(9090);

	@Bean
	@ServiceConnection
	MySQLContainer<?> mysqlContainer() {
		return new MySQLContainer<>(DockerImageName.parse("mysql:lts"));
	}

	@SneakyThrows
	@DynamicPropertySource
	static void dynamicProperties(DynamicPropertyRegistry registry, MySQLContainer<?> mysqlContainer) {
		registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mysqlContainer::getUsername);
		registry.add("spring.datasource.password", mysqlContainer::getPassword);

//TODO This don't work. Need good way to solve grpc port conflict.
//		registry.add("spring.grpc.server.port", () -> {
//			int port = grpcPort.getAndIncrement();
//			System.out.println("TestcontainersConfiguration.dynamicProperties: grpcPort.getAndIncrement():" + port);
//			return port;
//		});
	}

}
