package vn.edu.eaut.lab11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab11Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab11Application.class, args);
        System.out.println("=================================================");
        System.out.println("🚀 Lab 11 Spring Boot Application Started Successfully!");
        System.out.println("👉 Access UI at: http://localhost:8080");
        System.out.println("=================================================");
    }
}
