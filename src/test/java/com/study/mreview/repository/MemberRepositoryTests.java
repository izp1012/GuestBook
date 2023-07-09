package com.study.mreview.repository;

import com.study.mreview.repository.MemberRepository;
import com.study.mreview.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.Commit;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMembers() {

        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("r"+i +"@inhyo.org")
                    .pw("1111")
                    .nickname("reviewer"+i).build();
            memberRepository.save(member);
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteMember() {

        Long mid = 1L; //Member의 mid

        Member member = Member.builder().mid(mid).build();

        //기존
        memberRepository.deleteById(mid);
        reviewRepository.deleteByMember(member);
        /*
        위의 순서로 발생하면 에러가 발생
        에러원인
        1) FK 를 가지는 Review 쪽을 먼저 삭제하지않았음리
        2) 트랜잭션 관련 처리가 없다
        -> 아래의 순서로 메소드 수정 및 트랜잭션 관련 어노테이션인 @Transaction 이랑 @commit 을 추가해준다
         */
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
        //이렇게만 작성하면 delete 구문을 3번연속 날리게 되는데 이렇게 작동하는것은 비효율적이기 떄문에
        //@Query 를 사용하여 where 절을 지정하는게 더 효율적인 방식이다.
    }

}
