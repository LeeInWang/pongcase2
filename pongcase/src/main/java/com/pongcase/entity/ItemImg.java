package com.pongcase.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="item_img")
@Getter
@Setter
public class ItemImg extends BaseEntity {

    //이미지 파일명
    //원본 이미지 파일명
    //이미 조회 경로
    // 대표 이미지 여부 - Y(main 페이지에서 상룸을 보여줄 때 사용)/N

    @Id
    @Column(name="item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName; //이미지 파일명
    private String originImgName;  //원본 이미지 파일명
    private String imgUrl;  //이미지 조회 경로
    private String repImgYn; //대표 이미지 여부

    // @ManyToOne : 상품(item) 엔티티와 다대일 단방향 관계로 매핑
    // FetchType.LAZY : 지연로딩, 매핑된 상품 엔티티 정보가 필요할 경우 데이터를 조회
    //                  사용사점에 쿼리문이 실행
    // FetchType.EAGER : 즉시 로딩, 상품 엔티티가 함께 쿼리가 실행되어 함께 불려와짐
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //원본이미지 파일명, 업데이트할 이미지 파일명, 이미지 경로를 파라미터로 입력 받아서
    // 이미지 정보를 업데이트
    public void updateItemImg(String originImgName, String imgName, String imgUrl){
        this.originImgName = originImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }


}
