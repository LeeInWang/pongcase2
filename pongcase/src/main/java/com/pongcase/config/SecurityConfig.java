package com.pongcase.config;

import com.pongcase.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //< 요청에 대한 접근 권한을 설정 >
        //#1. 로그인 또는 인증 없이 들어갈 페이지 : 정적 리소스 부분, css, img, js, 상품 상세 페이지 조회..
        //# 2. 로그인 인증 해야 볼 수 있는 페이지 : 상품 주문, 장바구니,게시판 글쓰기,..
        //#3. 관리자 권한이 필요한 페이지 : 상품등록, 회원관리, 게시판 관리, 관리....

        // 모든 사용자에게 허용 : css, js, img,  members, item, imgages
        // Admin역할을 가진 사용자에게만 허용 : /admin/**
        // 나머지 모든 요청은 인증된 사용자에게만 허용, 허용이외의 것은 모든 요청에 대해 기본적인 보안규칙을 정의
//        http.authorizeRequests()
//                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
//                .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated() ;


        //사용자 정의 로그인 페이지 및 로그인 처리를 설정
//        http.formLogin()
//                .loginPage("/members/login")   //사용자가 인증되지 않은 경우 이 페이지로 리다이렉트됨
//                .defaultSuccessUrl("/")
//                .usernameParameter("userid")  //email
//                .failureUrl("/members/login/error")
//                .and()  //로그인 설정과 로그아웃 설정을 연결하기 위한 메소드
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그 아웃 요청패턴
//                .logoutSuccessUrl("/");
//        //인증이 필요한 리소스에 접근할 때 발생하는 예외 처리-사용자 인증 설정
//       http.exceptionHandling()
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint() );
//
//        //SecurityFilterChain을 반환
//        return  http.build();


        http.formLogin((formLogin) -> formLogin
                        .usernameParameter("userid")
                        .failureUrl("/members/login/error")
                        .loginPage("/members/login") // 로그인 페이지 설정
                        .defaultSuccessUrl("/"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true));


        http.authorizeRequests()
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();


        http.exceptionHandling(authenticationManager -> authenticationManager
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return http.build();
    }



    //비밀번호를 데이터베이스에 암호화하여 저장
    //BCryptPasswordEncoder의 해시 함수를 이용하여 비밀번호를 암호화하여 저장
    // 스프링 프레임워크에서 제공하는 클래스 중 하나로 비밀번호를 암호하는데 사용할 수 있는 메소드를
    //가진 클래스
    // 자바 서버 개발을 위해 필요한 인증, 권한 부여 및 기타 보안 기능을 제공하는 프레임워크
    //BCryptPasswordEncoder를 빈으로 등록해서, 스프링 컨테이너에 등록
    // Spring 컨테이너에 의해 관리되는 빈을 생성
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }


}
// 참고자료 : 스프링 부트 쇼핑몰 프로젝트 with jpa,  출판사: 로드북