package com.pongcase.service;

import com.pongcase.entity.Member;
import com.pongcase.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
//비즈니스 로직을 담당하는 서비스 계층 클래스에 선언
//로직을 처리하다가 에러가 발생하였다면, 변경된 로직을 수행하기 이전 상태로 콜백 시켜 줌
@Transactional
//@RequiredArgsConstructor

//UserDetailsService  : 데이터베이스에서 회원 정보를 가져오는 역할을 담당
public class MemberService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    //빈에 생성자가 1개이고, 생성자의 파라미터 타입이 빈으로 등록 가능하다면 @Autowired 어노테이션
    // 없이 의존성 주입이 가능
   // private final MemberRepository memberRepository;


    public Member saveMember(Member member){
        validateDuplicateMember(member);
        System.out.println(member.toString());
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member){   //이미 가입된 회원에 대해 IllegalStateException 처리
        //1. id
        Member findMember = memberRepository.findMemberByUserid(member.getUserid());

        //2. email
       // Member findMember = memberRepository.findMemberByEmail(member.getEmail());

        //3. id와 email
        //Member findMember = memberRepository.findMemberByIdAndEmail(member.getId(), member.getEmail());

        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    //UserDetailsService는 loadUserByUsername() 메소드가 잇고, 회원정보를 조회하여 사용자의 정보와 권한을
    //갖는 UserDetails 인터페이스를 반환
    //UserDetails : 스프링 시큐리티에서 회원의 정보를 담기 위해 사용하는 인터페이스
    //            직접구현하거나 스프링시큐리티에서 제공하는 User 클래스를 사용
    //User 클래스는 UserDetails인터테이스를 구현하고  있는 클래스

    @Override //로그인할 유저의 userid를 파라미터로 전달 받을 것임
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException{
        Member member =memberRepository.findMemberByUserid(userid);
        //Member member =memberRepository.findMemberByEmail(email);
        if(member == null){
            throw new UsernameNotFoundException(userid);
            //throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getUserid())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();

    }
}
