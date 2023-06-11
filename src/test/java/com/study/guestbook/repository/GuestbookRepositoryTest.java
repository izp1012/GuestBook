package com.study.guestbook.repository;

import com.study.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import java.util.stream.IntStream;
@SpringBootTest
public class GuestbookRepositoryTest {

        @Autowired
        private GuestbookRepository guestbookRepository;

        @Test
        public void insertDummies(){

            IntStream.rangeClosed(1,300).forEach(i -> {
                Guestbook guestbook = Guestbook.builder()
                        .title("Inhyo's Title.." + i)
                        .content("Inhyo's Content.." + i)
                        .writer("Inhyo" + (i % 10))
                        .build();
                System.out.println(guestbookRepository.save(guestbook));
            });
        }

        @Test
        public void updateTest(){

            Optional<Guestbook> result = guestbookRepository.findById(300L);

            //존재하는 번호로 테스트
            if(result.isPresent()){

                Guestbook guestbook = result.get();

                guestbook.changeTitle("Changed Title....");
                guestbook.changeContent("Changed Content....");

                guestbookRepository.save(guestbook);
            }
        }

    }
