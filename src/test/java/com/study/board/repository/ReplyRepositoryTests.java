package com.study.board.repository;

import com.study.board.entity.Board;
import com.study.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    /*
    300개의 댓글을 1~100 사이의 번호로 추가 ( 마지막의 board_bno 컬럼 값이 임의의 번호)
     */
    public void insertReply(){
        IntStream.rangeClosed(1,300).forEach(i->{
            //1 부터 100까지의 임의의 번호
            long bno = (long)(Math.random()*100)+1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply...."+i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });
    }
}