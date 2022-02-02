package com.mingky.Board.service;

import com.mingky.Board.domain.Member;
import com.mingky.Board.dto.SignupDto;
import com.mingky.Board.repository.MemberRepository;
import com.mingky.Board.util.MemberUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService  implements UserDetailsService{
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(SignupDto signupDto) {
        return memberRepository.save(signupDto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optional = memberRepository.findByEmail(username);
        Member member = optional.orElseThrow(
                () -> new UsernameNotFoundException("미등록 계정")
        );

        return  new MemberUser(member);
    }
}
