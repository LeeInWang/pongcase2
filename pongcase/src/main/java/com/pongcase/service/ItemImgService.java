package com.pongcase.service;

import com.pongcase.entity.ItemImg;
import com.pongcase.repository.ItemImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional  //스프링 프레임워크에서 트랜잭션을 관리하기 위해 사용
public class ItemImgService {
    //@Value 어노테이션을 통해
    //application.properties 파일에 등록한 itemImgLocation 값ㅇ르 불러와서
    // itemImgLocation 변수에 넣어 줌
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String originImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        //StringUtils : 문자열이 null인지 ""인지 확인.
        //isEmpty() : ""와 같은 공백 문자열에 대해서는 false를 반환
        if(!StringUtils.isEmpty(originImgName)){
            //사용자가 상품의 이미지를 등록했다면 저장할 경로와  파일의 이름,파일을 파일의 바이트
            // 배열을 파일 업로드 파라미터로 uploadFile 메소드를 호출
            // 호출 결과 로컬에 저장된 파일의 이름을 imgName qustndp wjwkd
            imgName = fileService.uploadFile(itemImgLocation,originImgName,itemImgFile.getBytes());

            //저장한 상품 이미지를 불러올 경로를 설정
            // 외부 리소스를 불러오는 urlPatterns로 WebMvcConfig 클래스에서
            // '/images/**'를 설정해 주었고 , application.properties에서 설정한
            // uploadPath 프로퍼키 경로인 c:/cshop/ 아래 item 폴더에 이미지를 저장하므로
            // 상품 이미지를 불러오는 경로로 "/images/item/"를 붙여 줌
            imgUrl = "/images/item/" + imgName;
        }
        //입력 받은 상품 이미지 정보 저장
        //originImgName : 업로드했던 상품 이미지 파일의 원래 이름
        //imgName : 실제 로컬에 저장된 상품 이미지 파일의 이름
        //imgUrl : 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러오는 경로
        itemImg.updateItemImg(originImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    //상품 이미지 데이터를 수정할 때는 변경감지 기능을 처리
    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
        //상품 이미지를 수정한 경우 상품 이미지를 업데이트
        if(!itemImgFile.isEmpty()){
            //상품 이미지 아이디를 이용해서 기존에 저장했던 상품 이미지 엔티티를 조회
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존에 등록한 상품 이미지 파일이 있는 경우 해당 파일을 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgName())){
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String originImgName = itemImgFile.getOriginalFilename();
            //업데이트한 상품 이미지 파일을 업로드
            String imgName = fileService.uploadFile(itemImgLocation,originImgName,
                    itemImgFile.getBytes());

            //상품 이미지를 불러오는 경로
            String imgUrl= "/images/item/" + imgName;


            //변경된 상품 이미지 정보를 세팅
            savedItemImg.updateItemImg(originImgName, imgName, imgUrl);

        }
      }
}
