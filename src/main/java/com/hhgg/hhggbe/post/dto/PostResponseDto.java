package com.hhgg.hhggbe.post.dto;

import com.hhgg.hhggbe.comment.dto.CommentDto;
import com.hhgg.hhggbe.post.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostResponseDto {
    private Long postId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String content;
    private Long visit;
    private String imageUrl;
    private List<CommentDto> comments = new ArrayList<>();

    public PostResponseDto(Post post, List<CommentDto> comments){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.createdAt = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();
        this.content = post.getContent();
        this.visit = post.getVisit();
        this.imageUrl = post.getImageUrl();
        this.comments = comments;
    }
}
