package com.study.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.study.board.entity.Board;
import com.study.board.entity.QBoard;
import com.study.board.entity.QMember;
import com.study.board.entity.QReply;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage.............................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        //SELECT b, w, count(r) FROM Board b
        //LEFT JOIN b.writer w LEFT JOIN Reply r ON r.board = b
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);

        booleanBuilder.and(expression);

        if(type != null){
            String[] typeArr = type.split("");
            //검색 조건을 작성하기
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t:typeArr) {
                switch (t) {
                    case "t" -> conditionBuilder.or(board.title.contains(keyword));
                    case "w" -> conditionBuilder.or(member.email.contains(keyword));
                    case "c" -> conditionBuilder.or(board.content.contains(keyword));
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);
        tuple.groupBy(board);
        List<Tuple> result = tuple.fetch();

        log.info(result);

        return null;
    }
}
