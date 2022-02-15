package com.mingky.Board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDto {
    private String comment;


    @Builder
    public CommentDto(String comment){
        this.comment = comment;

    }
}
