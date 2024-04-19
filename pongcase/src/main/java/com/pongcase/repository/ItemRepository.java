package com.pongcase.repository;

import com.pongcase.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

//Entity 클래스명, 기본키 타입
public interface ItemRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    //쿼리 메소드 작성하기
    //find + (엔티티명) + By + 검색에 사용할 변수명

   //상품의 이름을 이용해서 데이터를 조회
     //List<Item> findByItemName(String itemName);
     List<Item> findItemByItemName(String itemName);


     //Spring Data JPA @Query 어노테이션
    /*
        1. 쿼리 메소드 작성하기
            //find + (엔티티명) + By + 검색에 사용할 변수명

        2. Spring Data JPA @Query 어노테이션
            @Query 어노테이션을 이용하면 SQL과 유사한
                JPQL(Java Persistence Query Language)이라는 객체 지향 쿼리 언어를
                이용하여 복잡한 쿼리도 처리 가능
                SQL과 문법 자체가 유사
                SQL은 데이터베이스 테이블을 대상으로 쿼리를 수행
                JPQL은 엔티티 객체를 대상으로 쿼리를 수행

     */
     // 상품 상세 설명을  파라미터로 받아 해당 내용을 상품 상세 설명에 포함하고 있는
    // 데이터를 조회하고 가격이 높은 순으로 정렬해서 조회
    // i는 별칭
    //@Param 어노테이션을 이용하여 파라미터로 넘어온 값을 jpql에 들어갈 변수로 지정
    // itemDetail 변수를 like % % 사이의 :itemDetail 로 값이 들어가도록 설정함
    @Query("select i from Item i where i.itemDetail like %:itemDetail% " +
            " order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);


  /*
    //기존의 데잍터베이스에서 사용하던 쿼리를 그대로 사용하고 싶을 때
    // @Query의 nativeQuery 속성을 사용
      @Query(value="select i from Item i where i.itemDetail like %:itemDetail% " +
            " order by i.price desc", nativeQuery=true)
        List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);


    //Querydsl : JPQL을 코드로 작성할 수 있도록 도와주는 빌더 API
              SQL문을 문자열이 아닌 코드로 작성하기 때문에 컴파일러의 도움을 받을 수 있음
              동적으로 쿼리를 생성할 수 있음


        JPAQuery 데이터 반환 메소드
            List<T> fetch() : 조회 결과 리스트 반환
            T fetchOne : 조회 대상이 1건인 경우 제네릭으로 지정한 타입 반환
            T fetchFirst() : 조회 대상 중 1건만 반환
            Long fetchCount() : 조회 대상 개수 반환
            QueryResult<T> fetchResults() : 조회한 리스트와 전체 개수를 포함한 QueryResults 반환
  */

 }