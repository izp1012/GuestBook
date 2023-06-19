package com.study.guestbook.service;

import com.study.guestbook.dto.GuestbookDTO;
import com.study.guestbook.dto.PageRequestDTO;
import com.study.guestbook.dto.PageResultDTO;
import com.study.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister(){

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title ....")
                .content("Sample Contents....")
                .writer("Inhyo0...")
                .build();

        System.out.println(service.register(guestbookDTO));

    }
    //목록 처리 테스트
    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

//        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
//            System.out.println(guestbookDTO);
//        }
//        //테스트 결과를 보면 Page<Guestbook> 이 List<GuestBookDTO> 로 정상적으로 변환되어 출력결과에 GuestbookDTO 타입으로 출력되는것 확인된다.
//        GuestbookDTO(gno=301, title=Sample Title ...., content=Sample Contents...., writer=Inhyo0..., regDate=2023-06-11T23:55:38.656576, modDate=2023-06-11T23:55:38.656576)
//        GuestbookDTO(gno=300, title=Changed Title...., content=Changed Content...., writer=Inhyo0, regDate=2023-06-11T17:34:45.357998, modDate=2023-06-11T17:35:03.222849)
//        GuestbookDTO(gno=299, title=Inhyo's Title..299, content=Inhyo's Content..299, writer=Inhyo9, regDate=2023-06-11T17:34:45.296855, modDate=2023-06-11T17:34:45.296855)
//        GuestbookDTO(gno=298, title=Inhyo's Title..298, content=Inhyo's Content..298, writer=Inhyo8, regDate=2023-06-11T17:34:45.225078, modDate=2023-06-11T17:34:45.225078)
//        GuestbookDTO(gno=297, title=Inhyo's Title..297, content=Inhyo's Content..297, writer=Inhyo7, regDate=2023-06-11T17:34:45.156505, modDate=2023-06-11T17:34:45.156505)
//        GuestbookDTO(gno=296, title=Inhyo's Title..296, content=Inhyo's Content..296, writer=Inhyo6, regDate=2023-06-11T17:34:45.030427, modDate=2023-06-11T17:34:45.030427)
//        GuestbookDTO(gno=295, title=Inhyo's Title..295, content=Inhyo's Content..295, writer=Inhyo5, regDate=2023-06-11T17:34:44.937649, modDate=2023-06-11T17:34:44.937649)
//        GuestbookDTO(gno=294, title=Inhyo's Title..294, content=Inhyo's Content..294, writer=Inhyo4, regDate=2023-06-11T17:34:44.872962, modDate=2023-06-11T17:34:44.872962)
//        GuestbookDTO(gno=293, title=Inhyo's Title..293, content=Inhyo's Content..293, writer=Inhyo3, regDate=2023-06-11T17:34:44.763754, modDate=2023-06-11T17:34:44.763754)
//        GuestbookDTO(gno=292, title=Inhyo's Title..292, content=Inhyo's Content..292, writer=Inhyo2, regDate=2023-06-11T17:34:44.650255, modDate=2023-06-11T17:34:44.650255)

        //현재 데이터 상황 :  총 301 건의 데이터가 존재하고 각 페이지당 10개의 목록을 보여준다
        System.out.println("PREV : " + resultDTO.isPrev()); //false 1페이지이므로 이전으로 가능 링크 필요 없음
        System.out.println("NEXT : " + resultDTO.isNext()); //true 다음 페이지로 가능 링크 필요
        System.out.println("TOTAL : " + resultDTO.getTotalPage()); // 31 전체 페이지 갯수
        System.out.println("=====================================");
        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
        System.out.println("=====================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

    //목록 데이터 페이지 처리\
    /*
    화면까지 전달되는 데이터는 PageResultDTO 이고, 이를 이용해서 화면에서는 페이지 처리를 진행하게 될것
    PageResultDTO 타입으로 처리된 결과에는 시작 페이지, 끝 페이지 등 필요한 모든 정보를 담아서 화면에서는 필요한 내용들만 찾아서 구성이 가능하도록 작성한다.

    화면에 필요한 구성
    1. 10개씩 페이지 번호를 출력
    2. 10페이지 이후에는 이전으로 가능 링크 생성
    3. 마지막 페이지의 링크 계산
    - 화면에서 시작 페이지 번호(start)
    - 화면에서 끝 페이지 번호(end)
    - 이전 / 다음 이동 링크 여부 (prev, next)
    - 현재 페이지 번호(page)ßß

    시작페이지 번호보다는 끝 페이지 번호를 먼저 계산해두는 것이 수월하며
    다음과 같은 공식으로 구할 수 있다.
    tempEnd = (int)(Math.cell(페이지 번호 / 10.0)) * 10;

    끝번호는 아직 개선의 여지가 존재, 때문에 변수명을 tempEnd 로 설정한다
    만일 전체 데이터 수가 적어서 10페이지로 끝나면 안되는 상황이 생길수도 있기 때문에
    그럼에도 끝번호를 먼저 계산하는 이유는 시작 번호를 계산하기 수웛하기 때문이다.
    만일 화면에서 10개씩 보여준다면 시작 번호는 무조건 임시로 만든 끝 번호에서 9라는 값을 뺸값이 되기 때문이다.
     */

    @Test
    public void testSearch(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("c") // 검색 조건 t,c,w,tc,tcw..
                .keyword("한수") //검색 키워드
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("---------------------------------------------------");
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }

        System.out.println("===================================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

}
