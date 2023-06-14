package com.study.guestbook.controller;

import com.study.guestbook.dto.PageRequestDTO;
import com.study.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor //자동 주입을 위한 Annotation
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index(){

        return "redirect:guestbook/list";
    }

    @GetMapping({"/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model){
        /*파라미터로 pageRequestDTO 를 사용하였는데,
        스프링 MVC 는 파라미터를 자동으로 수집해 주는 기능이 있기때문에,
        화면에서 page와 size 라는 파라미터를 전달하면 pageRequestDTO 객체로 자동으로 수집될 것이다.
         */

        log.info("list.........." + pageRequestDTO);
        System.out.println(pageRequestDTO);
        System.out.println(service.getList(pageRequestDTO));
        model.addAttribute("result", service.getList(pageRequestDTO));

//        return "/guestbook/list";
    }

    @GetMapping("/register")
    public void register(){
        log.info("register get...");
    }

    @GetMapping("/error")
    public void error(){
        log.info("error...");
    }
}
