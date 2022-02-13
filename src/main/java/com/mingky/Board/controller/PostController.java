package com.mingky.Board.controller;

import com.mingky.Board.domain.Category;
import com.mingky.Board.domain.File;
import com.mingky.Board.domain.Member;
import com.mingky.Board.dto.AnimalWriteDto;
import com.mingky.Board.dto.FreeUpdateDto;
import com.mingky.Board.dto.FreeWriteDto;
import com.mingky.Board.repository.PostRepository;
import com.mingky.Board.service.PostService;
import com.mingky.Board.util.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    @PutMapping("/board/free/read/{id}")
    public Long freeUpdate(@RequestBody FreeUpdateDto freeUpdateDto, @PathVariable Long id){
        return postService.update(freeUpdateDto, id);
    }

    @DeleteMapping("/board/free/read/{id}")
    public Long boardDelete(@PathVariable Long id){
        postService.delete(id);
        return id;
    }

    @PostMapping("/animal/write")
    public Long freeSave(@RequestBody AnimalWriteDto animalWriteDto, @CurrentMember Member member) {
        animalWriteDto.setMember(member);

        return postService.animalSave(animalWriteDto);
    }



}


