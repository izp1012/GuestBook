package com.study.mreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MreviewApplication {

    public static void main(String[] args) {
        System.out.println("===========================Hi MreviewApplication==================================");
        SpringApplication.run(MreviewApplication.class, args);
    }

}
