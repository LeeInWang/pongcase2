package com.pongcase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    @GetMapping("/products/proList")
    public String product(Model model){
        return "/memberproduct/memberProductPage";
    }

    @GetMapping("/products/proList1")
    public String product1(Model model){
        return "/memberproduct/memberProductDetailPage";
    }
}
