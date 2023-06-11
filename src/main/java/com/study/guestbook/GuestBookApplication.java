package com.study.guestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GuestBookApplication {

    public static void main(String[] args) {
        System.out.println("===========================Hi GuestBookApplication==================================");
        SpringApplication.run(GuestBookApplication.class, args);
    }

}
