package com.study.mreview.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {        //화면에 필요한 모든 내용을 가져야함 -> 회원 아이디, 닉네임 이메일도 같이 처리할 수 있도록 설계

    //review num
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    //Movie mno
    private Long mno;

    //Member id
    private Long mid;

    //Member nickname
    private String nickname;

    //Member email;
    private String email;

    private int grade;

    private String text;

    private LocalDateTime regDate, modDate;
}