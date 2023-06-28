package com.study.board.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.study.board.entity.Board;
import com.study.board.entity.QBoard;
import com.study.board.entity.QMember;
import com.study.board.entity.QReply;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {
        log.info("search1........................");
//         JPQL 이용한 단순 SELECT
//        QBoard board = QBoard.board;
//
//        JPQLQuery<Board> jpqlQuery = from(board);
//        jpqlQuery.select(board).where(board.bno.eq(1L));
//
//        log.info("---------------------------");
//        log.info(jpqlQuery);
//        log.info("---------------------------");
//
//        List<Board> result = jpqlQuery.fetch();

//        JPQL 에서 OUTER JOIN 사용하기
//        QBoard board = QBoard.board;
//        QReply reply = QReply.reply;
//
//        JPQLQuery<Board> jpqlQuery = from(board);
//        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
//
//        log.info("---------------------------");
//        log.info(jpqlQuery);
//        log.info("---------------------------");
//
//        List<Board> result = jpqlQuery.fetch();

//        그룹바이 적용
//        QBoard board = QBoard.board;
//        QReply reply = QReply.reply;
//        QMember member = QMember.member;
//
//        JPQLQuery<Board> jpqlQuery = from(board);
//        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
//        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
//
//        jpqlQuery.select(board, member.email, reply.count())
//                .groupBy(board);
//
//        log.info("---------------------------");
//        log.info(jpqlQuery);
//        log.info("---------------------------");
//
//        List<Board> result = jpqlQuery.fetch();

        //최종 Tuple 적용
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board);

        log.info("---------------------------");
        log.info(tuple);
        log.info("---------------------------");

        List<Tuple> result = tuple.fetch();

        log.info(result);

        return null;
    }
}
