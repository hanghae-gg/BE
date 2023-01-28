package com.hhgg.hhggbe.riotAPI.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseDto {
    private int statuscode;
    private String message;
    private List<ApiResponseDto> data;

    public ResponseDto(String message, int statuscode, List<ApiResponseDto> match) {
        this.data = match;
        this.message = message;
        this.statuscode = statuscode;
    }
}