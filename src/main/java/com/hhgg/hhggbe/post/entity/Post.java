package com.hhgg.hhggbe.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hhgg.hhggbe.comment.entity.Comment;
import com.hhgg.hhggbe.post.dto.PostRequestDto;
import com.hhgg.hhggbe.timestamped.Timestamped;
import com.hhgg.hhggbe.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;
    private String content;
    private String imageUrl;
    private Long visit = 0L;
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();
    private boolean isDelete;

    public Post(PostRequestDto postRequestDto, String imageUrl, User user){
        this.imageUrl = imageUrl;
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.user = user;
    }

    public void PostPatch(PostRequestDto postRequestDto, String imageUrl){
        this.title = (postRequestDto.getTitle() == null) ? this.getTitle() : postRequestDto.getTitle();
        this.content = (postRequestDto.getContent() == null) ? this.getContent() : postRequestDto.getContent();
        this.imageUrl = (imageUrl == null) ? this.getImageUrl() : imageUrl;
    }
    public void PostPatchNoImage(PostRequestDto postRequestDto) {
        this.title = (postRequestDto.getTitle() == null) ? this.getTitle() : postRequestDto.getTitle();
        this.content = (postRequestDto.getContent() == null) ? this.getContent() : postRequestDto.getContent();
        this.imageUrl = this.getImageUrl();
    }
    public void PostDelete(){
        this.isDelete = true;
    }
    public void PostVisit() {
        this.visit += 1L;
    }
}
