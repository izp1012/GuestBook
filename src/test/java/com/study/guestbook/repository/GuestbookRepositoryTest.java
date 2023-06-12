package com.study.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.study.guestbook.entity.Guestbook;
import com.study.guestbook.entity.QGuestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

        //단일항목 검색 테스트
        @Test
        public void testQuery1(){

            Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

            //동적처리를 위해 Q도메인 클래스를 얻어온다.
            //엔티티 클래스에 선언된 필드를 변수로 사용 가능하다.
            QGuestbook qGuestbook = QGuestbook.guestbook;

            String keyword = "1";

            //BooleanBuilder는 where절에 조건들을 넣어주는 컨테이너
            BooleanBuilder builder = new BooleanBuilder();

            //필드값과 같이 결합하여 조건 사용
            BooleanExpression expression = qGuestbook.title.contains(keyword);

            //만들어진 조건을 and 나 or로 결합시킨다.
            builder.and(expression);

            //guestbookRepository에 추가된 QuerydslPredicateExecutor인터페이스의 findAll을 사용
            Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
            /* gno 역순으로 해서 10개를 찾을건데
             * guestbook.title 에 keyword(='1') 이 포함되어 있는 객체들을 찾아라
             */
            result.stream().forEach(guestbook -> {
                System.out.println(guestbook);
            });
        }

        @Test
        //특정한 keyword 가 있고 gno 가 0보다 크다
        public void testQuery2(){

            Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

            QGuestbook qGuestbook = QGuestbook.guestbook;

            String keyword = "1";

            BooleanBuilder builder = new BooleanBuilder();

            BooleanExpression exTitle = qGuestbook.title.contains(keyword);
            BooleanExpression exContent = qGuestbook.content.contains(keyword);

            //위에 두 조건을 or로 결함
            BooleanExpression exAll = exTitle.or(exContent);

            builder.and(exAll);

            //조건 1을 먼저 추가한 뒤 조건 2 추가
            //gt를 이용해 인자보다 크다라는 조건을 줌
            //gt 라는 메소드는 '>' 와 같은 의미임
            //lt 라는 메소드는 '<' 와 같은 의미임
            builder.and(qGuestbook.gno.lt(200L));

            Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
            /* gno 역순으로 해서 10개를 찾을건데
             * guestbook.title 에 keyword(='1') 이 포함되어 있는 객체들을 찾아라  //조건1
             * guestbook.content 에 keyword(='1') 이 포함되어 있는 객체들을 찾아라 //조건2
             *
             * BooleanExpression exAll = exTitle.or(exContent); 를 통해서 조건1과 조건2를 or 절로 묶고 난 후
             * BooleanBuilder와 BooleanExpression 2개의 객체를 and 로 합한다.
             */
            result.stream().forEach(guestbook -> {
                System.out.println(guestbook);
            });
        }

    }
