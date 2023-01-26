package com.hhgg.hhggbe.comment.entity;

import com.hhgg.hhggbe.comment.dto.CommentRequestDto;
import com.hhgg.hhggbe.post.Post;
import com.hhgg.hhggbe.timestamped.Timestamped;
import com.hhgg.hhggbe.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;   // 다른 id랑 헷갈릴거같아서 구분해봤습니다

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post post;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private boolean isReply = false;

    @Column(nullable = true)
    private Long referenceId;

    public Comment(User user, Post post, String comment, boolean isReply, Long referenceId) {
        this.user = user;
        this.post = post;
        this.comment = comment;
        this.isReply = isReply;
        this.referenceId = referenceId;
    }

    public void update(String comment) {
        this.comment = comment;
    }
}
