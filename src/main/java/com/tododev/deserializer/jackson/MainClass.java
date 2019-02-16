package com.tododev.deserializer.jackson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.tododev.deserializer")
public class MainClass {

    public static void main(String[] args) {
        SpringApplication.run(MainClass.class, args);
    }
}
