package com.study.board.repository;

import com.study.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>{

    //한개의 로우(Object) 내에 Object [] 로 나옴
    @Query("select b,w from Board b left join b.writer w where b.bno =:bno")
    Object getBoardWrithWriter(@Param("bno") Long bno);

    @Query("SELECT b, r From Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno =:bno ")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

}