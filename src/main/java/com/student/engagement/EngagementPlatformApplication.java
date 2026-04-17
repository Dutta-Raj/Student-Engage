package com.student.engagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EngagementPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(EngagementPlatformApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("?? Student Engagement Platform Ready!");
        System.out.println("========================================");
        System.out.println("?? Test the APIs:");
        System.out.println("   GET  http://localhost:8080/api/health");
        System.out.println("   POST http://localhost:8080/api/career/advice");
        System.out.println("   POST http://localhost:8080/api/loan/eligibility");
        System.out.println("   GET  http://localhost:8080/api/universities");
        System.out.println("========================================\n");
    }
}
