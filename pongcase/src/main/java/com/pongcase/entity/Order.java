

package com.pongcase.entity;

import com.pongcase.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //테이블이름 설정
@Getter @Setter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    //관계 매핑, 다대 일의 관계, 지연로딩(데이터를 가져오는 방식 중 하나),
    //fetch : 데이터를 가져오는 방식
    //지연로딩 : 사용시점에 쿼리문이 실행
    //즉시로딩 :
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //연결 컬럼
    private Member member; //참조 테이블

    private LocalDateTime orderDate;    //주문일

    @Enumerated(EnumType.STRING)    //이넘타입의 스트링 타입
    private OrderStatus orderStatus;

    // @OneToMany : 일대다
    //mappedBy : "order" 에 맵핑한다.
    //cascade : 부모 엔티티에 대한 모든 변경이 자식 엔티티에 적용 (연속된 처리)
    //orphanRemoval : 고아 제거(참조되지 않는 자식 엔티티들을 자동으로 삭제할지 여부를 설정) true면 자동삭제, false면 자동삭제 안됨 
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
            , orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();
    // OrderItem과 order

    //주문 상품 정보를 담아 놓음
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        //Order엔티티와 OrderItem 엔티티가 단방향*2 (양방향 참조 관계) 이므로
        //orderItem 객체에도 order 객체를 설정
        //this order엔티티하고 item은

    }

    //static으로 바로 사용 할 수 있도록 설정
    //Member와, orderItemList의 정보를 입력받아서 order에 추가하고 있습니다.
    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member); // 상품을 주문한 회원의 정보 설정

        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER); //주문상태를 ORDER로 설정
        order.setOrderDate(LocalDateTime.now()); //현재 주문 시간으로 설정
        return order;
    }
    //넘겨 받은 것을 order에 추가하고 있습니다.

    public int getTotalPrice() { // 주문 총 금액 구하기
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder() { //주문 취소
        this.orderStatus = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

}