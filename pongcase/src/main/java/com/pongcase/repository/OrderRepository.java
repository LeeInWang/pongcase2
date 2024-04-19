package com.pongcase.repository;


//기존에는 QueryDsl 사용했었는데, 지금은 어노테이션쿼리 사용 하려고합니다.
//QueryDsl -> @Query


import com.pongcase.entity.Order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


//주문 이력 조회
//@Query 안에 들어가는 문법은 JPQL 입니다.
public interface OrderRepository extends JpaRepository<Order, Long> {

    //쿼리 어노테이션 방식
    @Query("select o from Order o " +
            "where o.member.userid = :userid " +
            "order by o.orderDate desc"
    )
    
    //현재 로그인한 사용자의 주문 데이터를 페이징 조건에 맞춰서 조회
    List<Order> findOrders(@Param("userid") String userid, Pageable pageable);

    //쿼리 어노테이션 방식
    @Query("select count(o) from Order o " +
            "where o.member.userid = :userid"
    )
    // 현재 로그인한 회원의 주문 , 주문 개수가 몆개인지 조회
    Long countOrder(@Param("userid") String userid);
}