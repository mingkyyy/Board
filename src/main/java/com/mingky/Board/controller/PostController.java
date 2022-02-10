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

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping("/free/write")
    public Long freeSave(@RequestBody FreeWriteDto freeWriteDto, @CurrentMember Member member) {
        freeWriteDto.setMember(member);
        return postService.freeSave(freeWriteDto);
    }

    @PostMapping("/board/like")
    public Object boardLike(@RequestParam("id") Long id, @CurrentMember Member member) {
        Map<String, Object> map = new HashMap<>();
        int likeCount = postRepository.findById(id).orElseThrow().getLikers().size();
        String result = "실패";
        switch (postService.boardLike(member, id)) {
            case "addLike":
                result = "좋아요 성공";
                likeCount += 1;
                break;
            case "deleteLike":
                result = "좋아요 취소";
                likeCount -= 1;
        }

        map.put("result", result);
        map.put("likeCount", likeCount);

        return map;
    }

}


