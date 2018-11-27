package com.liberition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.liberition"})
public class MdwBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MdwBootApplication.class, args);
    }
}
