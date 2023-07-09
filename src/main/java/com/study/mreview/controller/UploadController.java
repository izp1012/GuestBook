package com.study.mreview.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {
    @Value("${org.inhyo.upload.path}")  //applicaion.properties 의 변수
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public void uploadFile(MultipartFile[] uploadFiles){

        for(MultipartFile uploadFile : uploadFiles){

            //이미지 파일만 업로드 가능
            if(uploadFile.getContentType().startsWith("image") == false) {
                log.warn("this file is not image type");
                return;
            }

            //실제 파일 이름 IE 나 Edg량e 는 전체 경로가 들어오므로
            String originlName = uploadFile.getOriginalFilename();
            String fileName = originlName.substring(originlName.lastIndexOf("\\")+1);

            log.info("fileName : "+fileName);

            //날짜 폴더 생성
            String folderPath = makeFolder();

            //UUID
            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 "_"를 이용해서 구분
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid +"_" + fileName;
            Path savePath = Paths.get(saveName);

            try{
                uploadFile.transferTo(savePath);
            }catch (IOException e){
                e.printStackTrace();
            }
        }//end for
    }

    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath =  str.replace("//", File.separator);

        // make folder --------
        File uploadPathFolder = new File(uploadPath, folderPath);

        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}
