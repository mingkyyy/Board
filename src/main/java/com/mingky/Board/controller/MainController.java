package com.mingky.Board.controller;

import com.mingky.Board.domain.Category;
import com.mingky.Board.domain.Member;
import com.mingky.Board.domain.MemberType;
import com.mingky.Board.domain.Post;
import com.mingky.Board.dto.SignupDto;
import com.mingky.Board.repository.MemberRepository;
import com.mingky.Board.repository.PostRepository;
import com.mingky.Board.service.MemberService;
import com.mingky.Board.service.PostService;
import com.mingky.Board.util.CurrentMember;
import com.mingky.Board.util.JoinValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostService postService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;

    @PostConstruct
    @Transactional
    public void createPost(){
        Member member = Member.builder()
                .name("홍길동")
                .email("test@gmail.com")
                .password(passwordEncoder.encode("test12345!"))
                .tel("0100000")
                .nickname("테스트")
                .memberType(MemberType.ROLE_MEMBER)
                .build();

        memberRepository.save(member);

        for (int i =0; i<30; i++) {
            Post post = Post.builder()
                    .title((i+1)+"번째 제목")
                    .content((i+1)+"번째 내용")
                    .write(memberRepository.getById(1l))
                    .category(Category.FREE)
                    .build();

            postRepository.save(post);
        }

    }

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
    public String freeBoard(@CurrentMember Member member, Model model,
                            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        Page<Post> freeList = postService.findCategoryPage(Category.FREE, pageable);
        model.addAttribute("freeList", freeList);
        model.addAttribute("pageable", pageable);

        int startPage = Math.max(1, freeList.getPageable().getPageNumber() - 5);
        int endPage = Math.min(freeList.getTotalPages(), freeList.getPageable().getPageNumber() + 5);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/board/free/list";

    }

    @GetMapping("/board/free/read/{id}")
    public String freeRead(Model model, @PathVariable Long id, @CurrentMember Member member){
        Post post = postService.findRead(id);
        post.setHit(post.getHit()+1);
        postRepository.save(post);
        model.addAttribute("post",post);
        model.addAttribute("member", member);
        return "/board/free/read";
    }

    @GetMapping("/board/animal/{category}")
    public String animalBoard(@CurrentMember Member member, @PathVariable String category){
        return "/board/animal/list";
    }

    @GetMapping("/board/free/write")
    public String freeWrite(@CurrentMember Member member){
        return "/board/free/write";
    }

    @GetMapping("/board/free/update/{id}")
    public String freeUpdate(Model model,@CurrentMember Member member, @PathVariable Long id){
        Post post = postRepository.findById(id).orElseThrow();
        model.addAttribute("post", post);
        return "/board/free/update";
    }
}
