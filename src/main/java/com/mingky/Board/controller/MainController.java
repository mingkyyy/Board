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
        model.addAttribute("member", member);
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
