package com.study.mreview.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Log4j2
public class UploadController {

    @PostMapping("/uploadAjax")
    public void uploadFile(MultipartFile[] uploadFiles){

        for(MultipartFile uploadFile : uploadFiles){
            //실제 파일 이름 IE 나 Edge 는 전체 경로가 들어오므로
            String originlName = uploadFile.getOriginalFilename();
            String fileName = originlName.substring(originlName.lastIndexOf("\\")+1);

            log.info("fileName : "+fileName);

        }//end for
    }
}
