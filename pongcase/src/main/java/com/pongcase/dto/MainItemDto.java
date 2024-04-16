//메인 페이지에서 상품을 보여줄 때 사용


package com.pongcase.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {

    private Long id;

    private String itemName;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    @QueryProjection  //select 절에 대상을 지정, Q타입의 Dtog를 사용
    //Projection : 테이블에서 원하는 컬럼만 뽑아서 조회하는 기능

    public MainItemDto(Long id, String itemName, String itemDetail, String imgUrl,Integer price){
        this.id = id;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }

}