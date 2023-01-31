package com.hhgg.hhggbe.comment.dto;

import com.hhgg.hhggbe.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String username;
    private String comment;

    public CommentDto(Comment comment) {
        this.id = comment.getCommentId();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
    }
}
