package com.hhgg.hhggbe.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentListDto {
    private List<CommentDto> comments = new ArrayList<>();

    public CommentListDto(List<CommentDto> comments) {
        this.comments = comments;
    }
}
