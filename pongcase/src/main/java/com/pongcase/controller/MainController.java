package com.pongcase.controller;


import com.pongcase.dto.ItemSearchDto;
import com.pongcase.dto.MainItemDto;
import com.pongcase.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class MainController {

    private  final ItemService itemService;

//    기존의 index
//    @GetMapping("/")
//    public String main(){
//        return "index";
//    }



// 변경된 index(페이징 처리)
    @GetMapping("/")    //Optional(맵퍼클래스) :  널포인트인셉션을 어느정도 막아주는 역할, 감싸주는 역할을 합니다.
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6); //최대 페이지 사이즈 6
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5); //최대 페이지 5

        return "index";
    }


}
