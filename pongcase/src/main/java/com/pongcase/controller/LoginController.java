package com.pongcase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class LoginController {



    //    로그인화면
    @GetMapping("/login")
    public String memberLogin(Model model){
        return "/member/memberLogin";
    }


    // 회원가입 화면(action form으로 받기에 requestMapping으로 설정했습니다.)
    @RequestMapping("/register")
    public String memberRegister(Model model){
        return "/member/memberRegister";
    }

    // 아이디찾기 화면
    @GetMapping("/findId")
    public String memberFindId(Model model){
        return "/member/memberFindId";
    }

    // 비밀번호찾기 화면
    @GetMapping("/findPassword")
    public String memberFindPassword(Model model){
        return "/member/memberFindPassword";
    }

    // 회원가입 약관 화면
    @GetMapping("/agreement")
    public String memberAgreement(Model model){
        return "/member/memberAgreement";
    }








}