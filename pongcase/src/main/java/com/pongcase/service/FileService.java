package com.pongcase.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {  //파일과 관련된 작업을 수행하는 서비스 클래스
    // 파일 업로드, 파일 다운로드, 삭제 등과 같은 파일 관련 비즈니스 로직을 처리하는 데 사용

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
       //범용 고유 식별자(Universally Unique Identifier, UUID) 는 해당 타입의 다른
        // 모든 리소스 중에서 리소스를 고유하게 식별하는 데 사용되는 레이블
        // 파일의 이름 중복 문제를 해결하기 위해 사용
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        //uuid로 받은 값과 원래 파일 이름의 확장자를 조합해서 저장될 파일 이름을 만들기
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;  //업로드된 파일 이름을 반환
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);  // 파일이 저장된 경로를 이용해서 파일 객체를 생성
        if(deleteFile.exists()) {  //해당 파일이 존재하면 파일을 삭제
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }




}
