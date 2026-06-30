package com.liverpool.exam;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

public abstract class AbstractIntegrationTest {
    private static MongoDBContainer mongo = null;
    private static boolean testcontainersStarted = false;

    static {
        try {
            MongoDBContainer c = new MongoDBContainer("mongo:6.0.28");
            c.start();
            mongo = c;
            testcontainersStarted = true;
        } catch (Throwable t) {
            // Testcontainers/Docker not available — fall back to embedded Mongo (flapdoodle)
            testcontainersStarted = false;
        }
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        if (testcontainersStarted && mongo != null) {
            registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
        }
        else {
            // When Testcontainers isn't available, configure embedded Mongo version for flapdoodle
            registry.add("spring.mongodb.embedded.version", () -> "4.4.6");
        }
    }
}

