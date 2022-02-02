package com.mingky.Board.controller;

import com.mingky.Board.dto.SignupDto;
import com.mingky.Board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public Long save(@RequestBody SignupDto signupDto){
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        return memberService.save(signupDto);
    }


}
