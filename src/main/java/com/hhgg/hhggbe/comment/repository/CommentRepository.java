package com.hhgg.hhggbe.comment.repository;

import com.hhgg.hhggbe.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findAllByPost_PostIdAndDeletedAtIsNull(Long postId);
    Optional<Comment> findByCommentIdAndDeletedAtIsNull(Long commentId);
    Optional<List<Comment>> findByReferenceIdAndDeletedAtIsNull(Long referenceId);
}
