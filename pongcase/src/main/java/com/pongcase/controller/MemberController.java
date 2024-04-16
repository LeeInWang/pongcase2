package com.pongcase.controller;


import com.pongcase.dto.MemberFormDto;
import com.pongcase.entity.Member;
import com.pongcase.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());

        return "member/memberForm";  //view 페이지
    }

    @PostMapping("/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);

        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";   //return "index";
    }


    @GetMapping("/login")
    public String loginMember(){
        return "/member/loginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디와 비밀번호를 확인");
        return "/member/loginForm";
    }

}

/*
       연관 관계 매핑
       1. 관계수의 종류 :
            일대일(1:1) : @OneToOne
            일대다(1:N) : @OneToMany
            다대일(N:1) : @ManyToOne
            다대다(N:M) : @ManyToMany

       2. 관계의 방향성
          관계성 모델 : 양방향
          객체 모델 : 단방향,  단방향*2 -> 양방향







 */





































