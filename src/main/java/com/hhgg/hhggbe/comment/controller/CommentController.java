package com.hhgg.hhggbe.comment.controller;

import com.hhgg.hhggbe.comment.dto.CommentListDto;
import com.hhgg.hhggbe.comment.dto.CommentRequestDto;
import com.hhgg.hhggbe.comment.dto.ResponseMessageDto;
import com.hhgg.hhggbe.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseMessageDto create(@PathVariable(value = "postId") Long id, @RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.create(id, request, userDetails.getUser());
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseMessageDto update(@PathVariable(value = "commentId") Long id, @RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.update(id, request, userDetails.getUser());
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseMessageDto delete(@PathVariable(value = "commentId") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.delete(id, userDetails.getUser());
    }
}
