package com.pongcase.controller;


import com.pongcase.constant.ItemSellStatus;
import com.pongcase.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {
    @GetMapping("/thy1")
    public String thyexam1(Model model){
        ItemDto itemDto = new ItemDto();

        itemDto.setItemName("상품1");
        itemDto.setItemDetail("상품 상세 설명1");
        itemDto.setStockNumber(100);
        itemDto.setPrice(2000);
        itemDto.setItemSellStatus(ItemSellStatus.SELL);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto",itemDto);
        return "thymeleaf/thyexample1";
    }
    @GetMapping("/thy2")  //  /thymeleaf/thy2
    public String thyexam2(Model model){

        List<ItemDto> itemList = new ArrayList<>();

        for(int i=1; i<=5; i++){
            ItemDto itemDto = new ItemDto();

            itemDto.setItemName("상품"+i);
            itemDto.setItemDetail("상품 상세 설명"+i);
            itemDto.setStockNumber(100*i);
            itemDto.setPrice(2000*i);
            itemDto.setItemSellStatus(ItemSellStatus.SELL);
            itemDto.setRegTime(LocalDateTime.now());

            itemList.add(itemDto);
        }
        model.addAttribute("itemList",itemList);
        return "thymeleaf/thyexample2";
    }

    @GetMapping("/thy3")
    public  String thyexam3( ){

        return "thymeleaf/thyexample3";
    }
    @GetMapping("/thy4")
    public  String thyexam4(String fruit, String hobby, Model model){
            model.addAttribute("fruit", fruit);
            model.addAttribute("hobby", hobby);
        return "thymeleaf/thyexample4";
    }
}
