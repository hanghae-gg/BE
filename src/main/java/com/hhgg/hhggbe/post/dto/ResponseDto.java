package com.hhgg.hhggbe.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto { //
    private boolean result;

    //public ResponseDto() {
    //this.result = true;
    //}

    public void ResponseTrue() {
        this.result = true;
    }

    public void ResponseFalse() {
        this.result = false;
    }
}