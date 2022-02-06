package com.mingky.Board.controller;

import com.mingky.Board.domain.Category;
import com.mingky.Board.domain.Member;
import com.mingky.Board.domain.Post;
import com.mingky.Board.dto.SignupDto;
import com.mingky.Board.repository.MemberRepository;
import com.mingky.Board.service.MemberService;
import com.mingky.Board.service.PostService;
import com.mingky.Board.util.CurrentMember;
import com.mingky.Board.util.JoinValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostService postService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @InitBinder("signupDto")
    protected void initBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(new JoinValidator(memberRepository));
    }

    @GetMapping("/")
    public String index(Model model, @CurrentMember Member member){
        model.addAttribute("member", member);
        return "index";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("signupDto", new SignupDto());
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupDto signupDto, Errors errors){
        if (errors.hasErrors()) {
            return "/member/signup";
        }
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        memberService.save(signupDto);
        return "redirect:/login";
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

    @GetMapping("/board/free")
    public String freeBoard(@CurrentMember Member member, Model model){
        Category category = Category.FREE;
        List<Post> freeList = postService.findCategory(category);
        model.addAttribute("freeList", freeList);
        return "/board/free/list";
    }

    @GetMapping("/board/animal/{category}")
    public String animalBoard(@CurrentMember Member member, @PathVariable String category){
        return "/board/animal/list";
    }

    @GetMapping("/board/free/write")
    public String freeWrite(@CurrentMember Member member){
        return "/board/free/write";
    }

}
