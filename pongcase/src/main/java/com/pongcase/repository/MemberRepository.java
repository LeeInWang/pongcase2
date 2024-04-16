package com.pongcase.repository;

import com.pongcase.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


//Member 엔티티를 데이터베이스에 저장할 수 있도록
public interface MemberRepository extends JpaRepository<Member, Long> {//entity, id타입
    //회원 가입시 중복된 회원이 있는지 검사,    JpaRepository<Member, String> //userid가 기본키일 경우

    //1. userid를 가지고 중복 검사
      Member findMemberByUserid(String userid);
      // 쿼리 메소드 :  find+entity명+By+변수명

    //2. email를 가지고 중복 검사
   // Member findMemberByEmail(String email);

    //3.user아이디와 email 두 가지를 가지고 중복 검사
   // Member findMemberByIdAndEmail(String userid, String email);

}