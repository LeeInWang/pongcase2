package com.pongcase.service;


import com.pongcase.dto.OrderDto;
import com.pongcase.entity.*;
import com.pongcase.repository.ItemRepository;
import com.pongcase.repository.MemberRepository;
import com.pongcase.repository.OrderRepository;
import com.pongcase.dto.OrderDto;
import com.pongcase.entity.Item;
import com.pongcase.entity.Member;
import com.pongcase.entity.Order;
import com.pongcase.entity.OrderItem;
import com.pongcase.repository.ItemRepository;
import com.pongcase.repository.MemberRepository;
import com.pongcase.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String userid){

        Item item = itemRepository.findById(orderDto.getItemId())   // 주문할 상품 조회
                .orElseThrow(EntityNotFoundException::new);         //찾고 그렇지않으면 예외를 던지게 설정했습니다.

        //현재 로그인한 회원의 userid 정보 이용해서 회원 정보를 조회
        Member member = memberRepository.findMemberByUserid(userid);

        List<OrderItem> orderItemList = new ArrayList<>();

        //주문할 상품 엔티티와 주문 수량을 이용해서 주문 상품 엔티티 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());

        orderItemList.add(orderItem);

        //회원 정보와 주문할 상품 리스트 정보를 이용하여 주문 엔티티 생성
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);    //생성한 주문 엔티티 저장

        return order.getId();
    }


}