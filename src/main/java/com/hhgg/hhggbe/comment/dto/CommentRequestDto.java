package com.hhgg.hhggbe.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    String comment;
    boolean isReply = false;
}
