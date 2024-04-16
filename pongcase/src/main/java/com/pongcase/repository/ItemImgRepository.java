package com.pongcase.repository;


// 상품 이미지 정보를 저장하기 위해

import com.pongcase.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    //매개 변수로 넘겨준 상품아이디를 가지며, 상품 아미지 아이디의 오름차순으로
    //가져오는 쿼리 메소드
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

     ItemImg findByItemIdAndRepImgYn(Long itemId, String repImgYn);

}
