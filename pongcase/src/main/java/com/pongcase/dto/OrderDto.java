package com.pongcase.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter
@ToString
public class OrderDto { // 상품 상세 페이지에 주문할 상품의 아이디와 주문 수량을 전달 받을 DTO 클래스

    @NotNull(message = "상품 아이디는 필수 입력")
    private Long itemId;

    @Min(value = 1, message = "최소 주문 수량은 1개")
    @Max(value = 999, message = "최대 주문 수량은 999개")
    private int count;

}