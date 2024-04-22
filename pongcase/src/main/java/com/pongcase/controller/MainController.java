package com.pongcase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String order(Model model){
        return "index";
    }
    @GetMapping("/quick")
    public String quick(Model model){return  "fragments/quickmenu";}

    @GetMapping("/arrow")
    public String arrow(Model model){return  "fragments/arrow";}
}
