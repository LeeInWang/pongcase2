//package com.pongcase.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration  //스프링의 구성으로 등록,  외부라이브러리 또는 내장 클래스를 bean으로 등록하고자 할 때
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    //application.properties에 설정한 "uploadPath" 프로퍼티 값 읽어오기
//    @Value("${uploadPath}")
//    String uploadPath;
//
//    //자신의 로컬 컴퓨터에 업로드한 파일을 찾을 위치를 설정
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//
//        // 웹 브라우저에서 입력하는 url에 /images로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로
//        // 파일을 읽어오도록 설정
//        registry.addResourceHandler("/images/**")
//                .addResourceLocations(uploadPath);  //로컬 컴퓨터에 저장된 파일을 읽어올
//                                                 //root경로를 설정
//    }
//}
