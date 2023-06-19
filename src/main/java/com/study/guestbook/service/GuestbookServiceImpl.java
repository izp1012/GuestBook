package com.study.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.study.guestbook.dto.GuestbookDTO;
import com.study.guestbook.dto.PageRequestDTO;
import com.study.guestbook.dto.PageResultDTO;
import com.study.guestbook.entity.Guestbook;
import com.study.guestbook.entity.QGuestbook;
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

//    @Override
//    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
//
//        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
//
//        Page<Guestbook> result = repository.findAll(pageable);

//        //entityToDto 를 이용해서 Function 객체를 생성
//        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

//        //fn 을 이용하여 PageResultDTO 로 구성.
//        return new PageResultDTO<>(result, fn);
//    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());

        //검색 조건 처리
        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);

        //요청 처리
        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        //받은 Entity -> DTO 변환
        Function<Guestbook, GuestbookDTO> fn = (entity ->
                entityToDto(entity));

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

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = requestDTO.getKeyword();

        // gno > 0 조건만 생성
        BooleanExpression expression = qGuestbook.gno.gt(0L);

        booleanBuilder.and(expression);

        //검색 조건이 없는 경우
        if(type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){//title 제목
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")){//contents 내용
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")){//writer 작성자
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
