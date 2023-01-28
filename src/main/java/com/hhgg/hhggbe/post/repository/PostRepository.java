package com.hhgg.hhggbe.post.repository;


import com.hhgg.hhggbe.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreateAtDesc();

    Optional<Post> findByPostId(Long postId);
}
