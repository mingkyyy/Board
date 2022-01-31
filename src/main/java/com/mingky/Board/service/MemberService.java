package com.mingky.Board.service;

import com.mingky.Board.dto.SignupDto;
import com.mingky.Board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(SignupDto signupDto) {
        return memberRepository.save(signupDto.toEntity()).getId();
    }
}
