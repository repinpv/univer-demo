package com.demo.univer.test;

import com.demo.univer.config.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class AppTests {

    @Test
    void contextLoads() {
    }
}
