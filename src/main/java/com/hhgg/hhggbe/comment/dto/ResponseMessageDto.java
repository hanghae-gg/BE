package com.hhgg.hhggbe.comment.dto;

import lombok.Getter;

@Getter
public class ResponseMessageDto {
    int status;
    String message;

    public ResponseMessageDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
