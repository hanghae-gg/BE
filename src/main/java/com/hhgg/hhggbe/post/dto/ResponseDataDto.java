package com.hhgg.hhggbe.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor

public class ResponseDataDto {
    private Integer status;
    private String message;
    private PostResponseDto postResponseDto;

    public ResponseDataDto(Integer status, String message, PostResponseDto postResponseDto) {
        this.status = status;
        this.message = message;
        this.postResponseDto = postResponseDto;
    }
}