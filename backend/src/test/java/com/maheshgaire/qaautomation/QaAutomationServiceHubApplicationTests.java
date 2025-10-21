package com.maheshgaire.qaautomation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.main.lazy-initialization=true")
class QaAutomationServiceHubApplicationTests {

    @Test
    void contextLoads() {
        // Test that the Spring context loads successfully
    }
}