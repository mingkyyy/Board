package com.mingky.Board.controller;

import com.mingky.Board.domain.Member;
import com.mingky.Board.util.CurrentMember;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model, @CurrentMember Member member){
        String message = "안녕하세요, 손님!";
        if(member != null){
            message = "안녕하세요, " + member.getName() + "님!";
        }
        model.addAttribute("member", member);
        model.addAttribute("message", message);

        return "index";
    }

    @GetMapping("/signup")
    public String signup(){
        return "member/signup";
    }

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }
}
