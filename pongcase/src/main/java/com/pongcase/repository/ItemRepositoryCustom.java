package com.pongcase.repository;

import com.pongcase.dto.ItemSearchDto;
import com.pongcase.dto.MainItemDto;
import com.pongcase.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
                               //상품조회 조건                     // 페이징 정보
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}
