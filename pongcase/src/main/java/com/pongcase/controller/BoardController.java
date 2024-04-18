package com.pongcase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {
    @GetMapping("/board")
    public String boardin(Model model){
        return "/memberboard/memberBoard";
    }
    @GetMapping("/faq")
    public String faqin(Model model){
        return "/memberboard/memberFaq";
    }
}
