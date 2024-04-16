



package com.pongcase.controller;


import com.pongcase.dto.OrderDto;
import com.pongcase.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;


//동기(나일하니깐, 기다려)
//비동기(같이하자)



//비동기(같이하자) 방식으로 처리
    @Controller
    @RequiredArgsConstructor
    public class OrderController {

        private final OrderService orderService;


        /*
            #
            스프링에서 비동기 처리할때 @ResponseBody , @RequestBody를 2개를 사용합니다.
            @ResponseBody  : 자바 객체를 HTTP 요청의 body로 전달
            @RequestBody : HTTP 요청의 본문 body에 담긴 내용을 자바 객체로 전달

            #
            @Valid : 데이터 유효성 검사(Validation)를 수행할 때 주로 사용됩니다.


            #
            BindingResult : 스프링이 제공하는 검증 오류를 보관하는 객체, 데이터 유효성 검사에
                            대한 에러 정보를 담음
                            정상적인 동작에서는 BindingResult에 담긴 오류 정보를 가지고
                            컨트롤러를 호출하고 없다면 400번대 오류가 발생하면서 컨트롤러 호출되지
                            않고 오류 페이지로 이동

                      bindingResult.hasErrors() : hasErrors()  boolean타입입니다. : 에러 유무를 판단


            #
            principal(Principal principal) : 스프링 시큐리티로 로그인된 사용자의 정보를 수정하기 위해
                     principal 객체를 이용해 사용자 정보를 불러옴, 이미 보호 받는 Resource에 접근하는 대상

            스프링 시큐리티 : 인증(Authentication) - 아이디, 비밀번호 , 본인인지 확인하는 절차
                            인가(Authorization)

                            (스프링 시큐리티는 기본적으로 인증(Authentication)을 처리하고
                            인증된 사용자가 자원에 접근 가능한지 인가(Authorization) 과정을 거친 후
                            해당 리소스에 접근 권한(Role)이 있는지 확인
                            이러한 인증과 인가를 위해 principal이 필요합니다.
                            
                            ex) 
                            인증(Authentication, 비밀번호와 이이디를 이용하여 본인이 맞는지 확인하는 절차) 
                            - > 
                            인가(Authorization, 인증된 사용자가 요청한 자원에 접근 가능한지를 결정하는 절차)



         */






        @PostMapping(value = "/order")
        public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto
                , BindingResult bindingResult, Principal principal){

            if(bindingResult.hasErrors()){ //주문 정보를 받는 orderDto 객체에 데이터      //에러메세지이면, 처리해준다.
                                            // 바인딩 시 에러가 있는지 검사
                StringBuilder sb = new StringBuilder();
                List<FieldError> fieldErrors = bindingResult.getFieldErrors(); // 바인딩 실패한 경우

                for (FieldError fieldError : fieldErrors) {
                    sb.append(fieldError.getDefaultMessage());
                }

                return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST); //HttpStatus.Series 400

            }

            //principal 객체에서 현재 로그인한 회원의 userid 조회
            //파라미터로 사용된 Principal principal로 인해 직접 접근할 수 있음
            //단, 현재 로그인 유저의 정보를 얻기 위해서는 @Controller 어노테이션이 선언된 클래스에서
            //메소드의 파라미터로 Principal 객체를 넘겨 줄 경우 해당 객체에 직접 접근할 수 있음
            String userid = principal.getName();

            Long orderId;


            try {
                //화면으로부터 넘어오는 주문 정보와 회원의 userid 정보를 이용해서 주문 로직을 호출
                orderId = orderService.order(orderDto, userid);

            } catch(Exception e){
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);  //HttpStatus.Series 400
            }

            return new ResponseEntity<Long>(orderId, HttpStatus.OK); //HttpStatus.Series 200
        }

    }