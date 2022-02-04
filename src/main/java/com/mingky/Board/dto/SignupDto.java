package com.mingky.Board.dto;

import com.mingky.Board.domain.Member;
import com.mingky.Board.domain.MemberType;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class SignupDto {

    private String name;

    private String email;

    private String nickname;

    private String password;

    private String tel;




    @Builder
    public SignupDto(String name, String email, String nickname, String password, String tel){
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.tel = tel;
    }


    public Member toEntity(){
        return Member.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .password(password)
                .tel(tel)
                .memberType(MemberType.ROLE_JUN)
                .build();
    }


}
