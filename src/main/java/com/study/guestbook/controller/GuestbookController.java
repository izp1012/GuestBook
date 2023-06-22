package com.study.guestbook.controller;

import com.study.guestbook.dto.GuestbookDTO;
import com.study.guestbook.dto.PageRequestDTO;
import com.study.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor //자동 주입을 위한 Annotation
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index(){

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model){
        /*파라미터로 pageRequestDTO 를 사용하였는데,
        스프링 MVC 는 파라미터를 자동으로 수집해 주는 기능이 있기때문에,
        화면에서 page와 size 라는 파라미터를 전달하면 pageRequestDTO 객체로 자동으로 수집될 것이다.
         */

        log.info("list.........." + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));

    }

    /*
     * 등록
     * */
    @GetMapping("/register")
    public void register(){
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto...." + dto);

        //새로 추가된 엔티티의 번호
        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    /*
     * 조회
     * */
    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDto") PageRequestDTO requestDTO, Model model){

        log.info("gno: " + gno);

        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto", dto);
        model.addAttribute("requestDTO", requestDTO);

    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){

        log.info("gno: " + gno+" 를 삭제합니다.");

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){

        log.info("post modify........................... ");
        log.info("dto" + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }


    @GetMapping("/error")
    public void error(){
        log.info("error...");
    }
}
