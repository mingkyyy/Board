package com.mingky.Board.controller;

import com.mingky.Board.domain.Member;
import com.mingky.Board.dto.CommentDto;
import com.mingky.Board.service.CommentService;
import com.mingky.Board.util.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {


    private final CommentService commentService;

    @PostMapping("/board/comment/{id}")
    public Long commentCreate(@RequestBody CommentDto commentDto, @PathVariable Long id, @CurrentMember Member
            member) {
        return commentService.saveComment(commentDto, id, member);


    }

}
