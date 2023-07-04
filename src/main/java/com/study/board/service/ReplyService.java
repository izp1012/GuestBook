package com.study.board.service;

import com.study.board.dto.BoardDTO;
import com.study.board.dto.ReplyDTO;
import com.study.board.entity.Board;
import com.study.board.entity.Member;
import com.study.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);   //댓글의 등록
    List<ReplyDTO> getList(Long bno);   //특정 게시물의 댓글 목록
    void modify(ReplyDTO replyDTO); //댓글 수정
    void remove(Long rno);  //댓글 삭제처리
    default Reply dtoToEntity(ReplyDTO replyDTO){

        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplayer())
                .board(board)
                .build();

        return reply;
    }

    //Reply 객체를 Reply로 변환
    //Board 객체가 필요하지 않으므로 게시물 번호만
    default ReplyDTO entityToDTO(Reply reply) {

        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replayer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();

        return dto;

    }
}
