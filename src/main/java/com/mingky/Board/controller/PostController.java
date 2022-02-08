package com.mingky.Board.controller;

import com.mingky.Board.domain.Member;
import com.mingky.Board.domain.Post;
import com.mingky.Board.dto.FreeWriteDto;
import com.mingky.Board.repository.PostRepository;
import com.mingky.Board.service.PostService;
import com.mingky.Board.util.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping("/free/write")
    public Long freeSave(@RequestBody FreeWriteDto freeWriteDto, @CurrentMember Member member){
        freeWriteDto.setMember(member);
        return postService.freeSave(freeWriteDto);
    }



}
