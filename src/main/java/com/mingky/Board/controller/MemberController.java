package com.mingky.Board.controller;

import com.mingky.Board.dto.SignupDto;
import com.mingky.Board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

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

    @GetMapping("/check/sendSMS")
    public String sendSMS(String phoneNumber) {

        Random rand = new Random();
        String numStr = "";
        for (int i = 0; i < 4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }
        System.out.println("수신자 번호 : " + phoneNumber);
        System.out.println("인증번호 : " + numStr);
        memberService.certifiedPhoneNumber(phoneNumber, numStr);
        return numStr;

    }




}
