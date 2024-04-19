package com.pongcase.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class MemberFormDto {
 /*
    @NotNull  : null이 아닌지 검사
    @NotBlank : null 인지 검사하고 문자열의 길이가 0 또는 ""(공란)인지 검사
    @Length(min= , max= ) : 최소 길이와 최대 길이를 검사
    @Email : email 형식인지 체크
    @Null : null인지 검사
    @Max(숫자) : 지정한 숫자보가 작은지 검사
    @Min(숫자) : 지정한 숫자보다 큰지 검사
    @NotEmpty : null 인지 체크하고 문자열의 경우 길이가 0인지 검사
  */

    private Long id;

    @NotBlank(message = "아이디는 필수 입력")
    @Length(min=4, max=10, message = "아이디는 4~10자 사이로 입력")
    private String userid;

    @NotBlank(message = "이름은 필수 입력")
    private String name;

    @NotBlank(message = "비밀번호는 필수 입력")
    @Length(min=8, max=20, message = "비밀번호는 8~20자 사이로 입력")
    private String password;

    @NotBlank
    @Email(message = "이메일 형식이 아닙니다. 이메일은 필수")
    private String email;

    @NotEmpty(message = "주소는 필수 입력")
    private String address;
}
