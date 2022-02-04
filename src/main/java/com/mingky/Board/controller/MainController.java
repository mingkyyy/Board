package com.mingky.Board.controller;

import com.mingky.Board.domain.Member;
import com.mingky.Board.util.CurrentMember;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/mypage/{nickname}")
    public String mypage(Model model, @CurrentMember Member member,
                         @PathVariable String nickname){

        if (member == null || !member.getNickname().equals(nickname)){
            return "redirect:/";
        }
        model.addAttribute("member", member);
        return "member/mypage";
    }
}
