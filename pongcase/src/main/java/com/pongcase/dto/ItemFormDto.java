package com.pongcase.dto;

import com.pongcase.constant.ItemSellStatus;
import com.pongcase.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ItemFormDto {
    private Long id;   //아이디, 기본키

    @NotBlank(message = "상품명은 필수 입력")
    private String itemName;  // 상품명

    @NotNull(message = "재고수량은 필수 입력")
    private Integer stockNumber; //재고 수량

    @NotBlank(message = "상품 상세 설명은 필수 입력")
    private String itemDetail; //상품 상세 설명

    @NotNull(message = "상품가격은 필수 입력")
    private Integer price;  //상품 가격

    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    //상품 저장 한 후 수정할 때 상품 이미지 정보를 저장하기 위한 리스트
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    //상품의 이미지 아이디를 저장하는 리스트
    //수정할 때 이미지 아이디를 담아둘 용도
    private List<Long> itemImgIds = new ArrayList<>();

    //DTO 객체로 바꿔주는 작업을 도와주는 라이브러리
    private static ModelMapper modelMapper = new ModelMapper();

    //modelMapper를 이용해서 객체와 DTO 객체 간의 데이터를 복사해서 복사한 객체를 반환해줌
    public Item createItem(){

        return modelMapper.map(this, Item.class);
    }

    //modelMapper를 이용해서 엔티티 객체와 DTO 객체 간의 데이터를 복사하여 복사한 객체를 반환
    public static ItemFormDto of(Item item){

        return modelMapper.map(item,ItemFormDto.class);
    }










}
