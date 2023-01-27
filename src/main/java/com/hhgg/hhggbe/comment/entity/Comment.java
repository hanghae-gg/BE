package com.hhgg.hhggbe.comment.entity;

import com.hhgg.hhggbe.post.entity.Post;
import com.hhgg.hhggbe.timestamped.Timestamped;
import com.hhgg.hhggbe.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;   // 다른 id랑 헷갈릴거같아서 구분해봤습니다
                              // good
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post post;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Boolean isReply = false;

    @Column
    private Long referenceId;

    @Column     //null 이면 살아잇음, null 이아니면 삭제되있음 <- 삭제된 시간이 들어감
    private LocalDateTime deletedAt = null;

    public Comment(User user, Post post, String comment, Boolean isReply, Long referenceId) {
        this.user = user;
        this.post = post;
        this.comment = comment;
        this.isReply = isReply;
        this.referenceId = referenceId;
    }

    public void update(String comment) {
        this.comment = comment;
    }

    public void commentDelete() {
        deletedAt = LocalDateTime.now();
    }
}
