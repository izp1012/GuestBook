package com.study.guestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GuestBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuestBookApplication.class, args);
        System.out.println("하이 스프링");
    }

}
