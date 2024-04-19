package com.pongcase.controller;


import com.pongcase.dto.ItemFormDto;
import com.pongcase.dto.ItemSearchDto;
import com.pongcase.entity.Item;
import com.pongcase.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
// import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/admin")
public class ItemController {

    private final ItemService itemService;




    //@GetMapping("/item/new")
    @GetMapping("/admin/item/new")  //반드시 admin 계정으로 로그인 해야 함
    public String itemForm(Model model){
        model.addAttribute("itemFormDto", new ItemFormDto());

        return "item/itemForm";
    }




    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model,@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){

        //상품 등록 시 필수 값이 없다면 다시 상품 등록 페이지로 전환
        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        //상품 등록 시 첫 번재 이미지가 없다면 에러 메시지와 함께 상품 등록 페이지로 전환
        //상품의 첫 번재 이미지는 메인 페이지에서 보여줄 상품 이미지로 사용해야 해서 필수 값으로 지정
        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
             model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입력");
             return "item/itemForm";
         }

        try {
            //상품을 저장하는 로직을 호출
            // 매개변수로 상품 정보와 상품 이미지 정보를 담고 있는  itemImgFileList를 넘겨 줌
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생");
            return "item/itemForm";
        }

        //상품이 정상적으로 등록되었다면 메인 페이지(welcome page)로 이동
        return "redirect:/";
    }




    ///============================
    @GetMapping(value="/admin/item/{itemId}")
    public String itemDtll(Model model, @PathVariable("itemId") Long itemId){

       try {
           //조회한 상품 데이터를 모델에 담아서 뷰로 전달
           ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
           model.addAttribute("itemFormDto", itemFormDto);

       }catch (EntityNotFoundException e){
           //상품 엔티티가 존재하지 않을 경우 에러 메시지를 담아서 상품 등록 페이지로 이동
           model.addAttribute("erroMessage", "존재하지 않는 상품");
           model.addAttribute("itemFormDto", new ItemFormDto());
           return "item/itemForm";
       }
        return  "item/itemForm";
    }



    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력.");
            return "item/itemForm";
        }

        //상품 수정 관련 로직
        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생");
            return "item/itemForm";
        }

        return "redirect:/";
    }

   // 상품 관리 화면 이동 및 조회한 상품 데이터를 화면에 전달
    // "/admin/items" - 상품 관리 화면 들어갈 때 URL에 페이지 번호가 없는 경우
    //"/admin/items/{page}" - 상품 관리 화면 들어갈 때 URL에 페이지 번호가 있는 경우
    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

                           //URL에 페이지 번호가 있으면 해당 페이지 번호 없으면 0페이지 조회 //한 페이지당 3개
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "item/itemMng";
    }



    /* 상품상세 페이지*/
    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemFormDto);
        return "item/itemDtl";
    }


}
































