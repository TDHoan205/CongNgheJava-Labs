package vn.edu.eaut.lab12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab12Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab12Application.class, args);
        System.out.println("=================================================");
        System.out.println("🚀 Lab 12 Spring MVC Application Started Successfully!");
        System.out.println("👉 Access Student App at: http://localhost:8080/students");
        System.out.println("=================================================");
    }
}
