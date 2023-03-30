package com.cs304.csfunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CsFundingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsFundingApplication.class, args);
    }

}
