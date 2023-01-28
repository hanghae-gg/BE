package com.hhgg.hhggbe.comment.controller;

import com.hhgg.hhggbe.comment.dto.CommentListDto;
import com.hhgg.hhggbe.comment.dto.CommentRequestDto;
import com.hhgg.hhggbe.comment.dto.ResponseMessageDto;
import com.hhgg.hhggbe.comment.service.CommentService;
import com.hhgg.hhggbe.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    CommentService commentService;

    @GetMapping("/comments/{postId}")
    public CommentListDto get(@PathVariable(value = "postId") Long id) {
        return commentService.get(id);
    }

    @PostMapping("/comments/{postId}")
    public ResponseMessageDto create(@PathVariable(value = "postId") Long id, @RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.create(id, request, userDetailsImpl.getUser());
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseMessageDto update(@PathVariable(value = "commentId") Long id, @RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
       return commentService.update(id, request, userDetailsImpl.getUser());
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseMessageDto delete(@PathVariable(value = "commentId") Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.delete(id, userDetailsImpl.getUser());
    }
}
