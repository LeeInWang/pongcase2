package com.pongcase.entity;

import com.pongcase.constant.Role;
import com.pongcase.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity {

    @Id   //기본키
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   //관계 매핑을 위해

    @Column(unique = true, nullable = false)
    private String userid;

//    @Id   //기본키
//    @Column(name="member_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long userid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = true)
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    //비밀번호를 암호화하는 작업
    //Member Entity를 생성하는 메소드
    //Member Entity에 회원을 생성하는 메소드를 만들어서 관리를 하게 되면
    // 코드가 변경되어도 한 군데만 수정하면 되기 때문에 사용
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setUserid(memberFormDto.getUserid());
        member.setName(memberFormDto.getName());

        //스프링 시큐리티 설정 클래스에 등록한 BCryptPasswordEncoder Bean를 파라미터로 넘겨서 비밀번호 암호화
        String pwd = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(pwd);
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setRole(Role.ADMIN);
        return member;
    }

}
