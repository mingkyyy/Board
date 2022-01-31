package com.mingky.Board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/signup")
    public String signup(){
        return "member/signup";
    }
}
