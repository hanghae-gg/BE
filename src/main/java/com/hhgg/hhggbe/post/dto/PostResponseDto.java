package com.hhgg.hhggbe.post.dto;

import com.hhgg.hhggbe.comment.dto.CommentListDto;
import com.hhgg.hhggbe.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class PostResponseDto {
    private long postId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String content;
    private Long visit;
    private String imageUrl;
    private List<CommentListDto> comments = new ArrayList<>();

    public PostResponseDto(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.createdAt = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();
        this.content = post.getContent();
        this.visit = post.getVisit();
        this.imageUrl = post.getImageUrl();
    }
}
