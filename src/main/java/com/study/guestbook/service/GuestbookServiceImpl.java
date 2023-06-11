package com.study.guestbook.service;

import com.study.guestbook.dto.GuestbookDTO;
import com.study.guestbook.entity.Guestbook;
import com.study.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor // 의존성 자동주입
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository; //반드시 final 로 선언
    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO-----------------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }
}
