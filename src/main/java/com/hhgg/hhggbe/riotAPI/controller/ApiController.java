package com.hhgg.hhggbe.riotAPI.controller;

import com.hhgg.hhggbe.riotAPI.dto.ApiRequestDto;
import com.hhgg.hhggbe.riotAPI.dto.ResponseDto;
import com.hhgg.hhggbe.riotAPI.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class ApiController {
    private final ApiService apiService;
    @PostMapping("/records")
    public ResponseDto match(@RequestBody ApiRequestDto apiRequestDto){
        String message = "전적 검색 완료";
        int statuscode = 200;
        ResponseDto responseDto = new ResponseDto(message, statuscode, apiService.match(apiRequestDto));
        return responseDto;
    }
}
