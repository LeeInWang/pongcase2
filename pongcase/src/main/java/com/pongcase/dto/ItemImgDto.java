package com.pongcase.dto;

import com.pongcase.entity.ItemImg;
import com.pongcase.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {
    private Long id;

    private String imgName; //이미지 파일명
    private String originImgName;  //원본 이미지 파일명
    private String imgUrl;  //이미지 조회 경로
    private String repImgYn;

    // 서로 다른 클래스의 값을 필드의 이름과 자료형이 같으면 getter, setter를 통해 값을 복사해서
    // 객체를 반환
    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg){
        //ItemImg의 필드들을 ItemImgDto의 필드들로 복사하고 매핑하는 역할
        return modelMapper.map(itemImg, ItemImgDto.class);
    }

}
