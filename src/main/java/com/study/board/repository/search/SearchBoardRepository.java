package com.study.board.repository.search;

import com.study.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {

    Board search1();

    //PageRequestDTO 를 자체 파라미터로 처리하지 않는 이유는 DTO 를 가능하면 Repository 영역에서 다루지 않기 위해서
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
