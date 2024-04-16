package com.pongcase.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity //테이블과 매핑시킴
@Getter
@Setter
public class OrderItem extends BaseEntity { //BaseEntity : 날짜 시간 처리

    @Id //프라이머리키로 사용한다.
  //  @GeneratedValue // 기본값을 알아서 처리하게 한다.
    @GeneratedValue(strategy = GenerationType.AUTO) // 오토인크리먼트
  //  @GeneratedValue(strategy = GenerationType.IDENTITY) //
 //   @GeneratedValue(strategy = GenerationType.SEQUENCE) //
  //  @GeneratedValue(strategy = GenerationType.IDENTITY) //
  //  @GeneratedValue(strategy = GenerationType.IDENTITY) //
    @Column(name = "order_item_id") //컬럼을 사용한다.
    private Long id; 



    /*
    #
    즉시로딩 : ???????????????????????????????????

    #
    지연로딩 :
    조인할떄,
     */

    //관계 맵핑 @ManyToOne(fetch = FetchType.LAZY) : 관계
    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "item_id") //조인컬럼(레퍼런스) : 연결할 테이블 ,참조할 레퍼런스 테이블 ,
    private Item item;  //Item테이블이랑 관계 설정(아이템테이블은 부모테이블) 연결한다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //???????????????????????????????????
    //JPA는 단방향 설정이라고하는데,
    //우리 구조는 Order.java에서 설정이 한번 더이루어집니다. 그래서 양방향 됩니다.

    private int orderPrice;

    private int count; //주문 수량

    //스타틱 타입으로 만들어놔서 바로 불러올수잇게 했습니다.
    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        
        orderItem.setItem(item);    //주문할 상품 설정
        orderItem.setCount(count);  //주문 수량 설정
        orderItem.setOrderPrice(item.getPrice()); // 현재 시간을 기준으로 상품 가격을 주문가격으로 설정하겠다.

        //Item 엔티티의 removeStock 메소드 호출
        item.removeStock(count);    //주문 수량 만큼 상품의 재고 수량을 감소
        return orderItem;
    }

    public int getTotalPrice(){ // 해당 상품을 주문한 총 가격
        return orderPrice*count;
    }

    public void cancel() {  //취소 시 재고 수량 증가
        this.getItem().addStock(count);
    }

}