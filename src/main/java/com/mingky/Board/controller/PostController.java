package com.mingky.Board.controller;

import com.mingky.Board.domain.Member;
import com.mingky.Board.dto.FreeWriteDto;
import com.mingky.Board.service.PostService;
import com.mingky.Board.util.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/free/write")
    public Long freeSave(@RequestBody FreeWriteDto freeWriteDto, @CurrentMember Member member){
        freeWriteDto.setMember(member);
        return postService.freeSave(freeWriteDto);
    }

}
