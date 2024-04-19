package com.pongcase.dto;
import com.pongcase.constant.ItemSellStatus;
import com.pongcase.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

// dto를 따로 만드는 이유
// 데이터를 주고 받을 때는 Entity 클래스 자체를 반환하면 안됨
//  DTO(Data Transfer Object, 데이터 전달용 객체, 수레)를 생성해서 사용
// 데이터베이스의 설계를 외부에 노출할 필요도 없으며, 요청과 응답 객체가
// 항상 Entity와 같지 않기 때문

@Setter
@Getter
@ToString
public class ItemDto {
    private Long id; //상품코드
    private String itemName; //상픔명
    private Integer price; //가격
    private Integer stockNumber; //재고수량
    private String itemDetail; //상품 상세 설명
    private ItemSellStatus itemSellStatus ;  // 상품 판매 상태
    private LocalDateTime regTime; //등록시간
    private LocalDateTime updateTime; // 수정 시간
}