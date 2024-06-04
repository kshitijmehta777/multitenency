package com.ms.multi_tenency_db_per_tenent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MultiTenencyDbPerTenentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiTenencyDbPerTenentApplication.class, args);
    }

}
