package com.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SmartTenoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartTenoApplication.class, args);
    }
}