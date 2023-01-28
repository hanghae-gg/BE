package com.hhgg.hhggbe.comment.dto;

import com.hhgg.hhggbe.comment.entity.Comment;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String comment;

    public CommentDto(Comment comment) {
        this.id = comment.getCommentId();
        this.comment = comment.getComment();
    }
}
