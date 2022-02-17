package com.mingky.Board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentUpdateDto {
    private String comment;

    @Builder
    public CommentUpdateDto(String comment){
        this.comment = comment;
    }
}
