package com.hhgg.hhggbe.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    String comment;
    boolean isReply = false;
}
