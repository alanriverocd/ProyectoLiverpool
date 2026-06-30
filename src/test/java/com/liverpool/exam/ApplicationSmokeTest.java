package com.liverpool.exam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationSmokeTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void actuatorHealthUp() {
        ResponseEntity<String> r = rest.getForEntity("/actuator/health", String.class);
        assertThat(r.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(r.getBody()).contains("UP");
    }
}
