package com.hhgg.hhggbe.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginErrorMessage {
    private Integer status;
    private String msg;
    private String accessToken;

    public LoginErrorMessage(String msg) {
        this.msg = msg;
    }

    public LoginErrorMessage(Integer status, String msg, String accessToken) {
        this.status = status;
        this.msg = msg;
        this.accessToken = accessToken;
    }
}