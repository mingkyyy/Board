package com.mingky.Board.oauth;

import com.mingky.Board.domain.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.List;
import lombok.Getter;

@Getter
public class MemberOAuth2User extends DefaultOAuth2User {

    private final Member member;

    public MemberOAuth2User(Member member, OAuth2Attributes attributes) {
        super(
                List.of(new SimpleGrantedAuthority(member.getMemberType().name())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );

        this.member = member;
    }

    @Override
    public String getName() {
        return member.getEmail();
    }
}

