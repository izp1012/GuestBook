package com.study.guestbook.service;

import com.study.guestbook.dto.GuestbookDTO;
import com.study.guestbook.dto.PageRequestDTO;
import com.study.guestbook.dto.PageResultDTO;
import com.study.guestbook.entity.Guestbook;
import com.study.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

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

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);
        //entityToDto 를 이용해서 Function 객체를 생성
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));
        //fn 을 이용하여 PageResultDTO 로 구성.
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO entityToDto(Guestbook entity) {

        return GuestbookService.super.entityToDto(entity);
    }

    @Override
    public GuestbookDTO read(Long gno) {

        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent()? entityToDto(result.get()): null;
    }

    /*
     * 삭제
     * */
    @Override
    public void remove(Long gno) {

        repository.deleteById(gno);

    }

    /*
     * 수정
     * */
    @Override
    public void modify(GuestbookDTO dto) {

        //업데이트 하는 항목은 '제목', '내용'
        Optional<Guestbook> result = repository.findById(dto.getGno());

        if(result.isPresent()){
            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }

    }
}
