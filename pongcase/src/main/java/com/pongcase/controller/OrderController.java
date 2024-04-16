package com.pongcase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class OrderController {
    @GetMapping("/order")
    public String order(Model model){
        return "memberorder/memberOrderPayment";
    }
}
