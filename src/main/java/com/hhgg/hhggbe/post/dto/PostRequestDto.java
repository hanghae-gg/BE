package com.hhgg.hhggbe.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Getter
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;

    @Override
    public String toString() {
        return "ArticleForm{"+
                "title='" + title + '\'' +
                "content='" + content +'\'' +
                '}';
    }
}
