package com.study.guestbook.service;

import com.study.guestbook.dto.GuestbookDTO;
import com.study.guestbook.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);


    /*
    defult 라는 접근 제한자 :
    Java8 부터는 인터페이스의 실제 내용을 가지는 코드를 dafault 라는 키워드로 생성할 수 있다.
    이를 통해서 기존에 추상 클래스를 통해서 전달해야 하는 실제 코드를 인터페이스에 선언할 수 있다.
    인터페이스 -> 추상클래스 -> 구현클래스의 단계에서 추상클래스 단계를 생략
     */
    default Guestbook dtoToEntity(GuestbookDTO dto){
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }
}
