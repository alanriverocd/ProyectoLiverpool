package com.liverpool.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
    /**
     * Spring Boot entry point.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * RestTemplate bean used by adapters to call external HTTP services.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
