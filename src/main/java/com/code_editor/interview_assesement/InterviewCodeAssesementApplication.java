package com.code_editor.interview_assesement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InterviewCodeAssesementApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewCodeAssesementApplication.class, args);
    }

}
