package com.study.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Builder
@AllArgsConstructor
@Data
/*
    이 클래스의 목적 : JPA 쪽에서 사용하는 Pageable 타입의 객체를 생성하는 것
    추후 수정의 여지 존재 (페이지 번호에 음수가 들어오는 경우 )
    그러나 JPA 이용하는 경우 페이지 번호가 0부터 시작한다는 점을 감안하여 1페이지의 경우 0이 되도록 page-1 하는 형태로 작성
 */

public class PageRequestDTO {
    private int page;
    private int size;

    public void PageRequestDTO(){
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1, size, sort);
    }
}
