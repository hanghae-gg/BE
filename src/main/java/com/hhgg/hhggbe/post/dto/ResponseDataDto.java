package com.hhgg.hhggbe.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor

public class ResponseDataDto {
    private Integer status;
    private String message;
    private Object Data;

    public ResponseDataDto(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        Data = data;
    }
}