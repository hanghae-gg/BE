package com.hhgg.hhggbe.comment.controller;

import com.hhgg.hhggbe.comment.dto.CommentListDto;
import com.hhgg.hhggbe.comment.dto.CommentRequestDto;
import com.hhgg.hhggbe.comment.dto.ResponseMessageDto;
import com.hhgg.hhggbe.comment.service.CommentService;
import com.hhgg.hhggbe.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Comment"})
@RestController
@RequiredArgsConstructor
public class CommentController {
    CommentService commentService;

    @ApiOperation(value = "댓글 받기", notes = "댓글들을 게시글의 id로 조회한다.")
    @GetMapping("/comments/{postId}")
    public CommentListDto get(@PathVariable(value = "postId") Long id) {
        return commentService.get(id);
    }

    @ApiOperation(value = "댓글 작성", notes = "해당 아이디의 게시글에 댓글을 추가한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PostMapping("/comments/{postId}")
    public ResponseMessageDto create(@PathVariable(value = "postId") Long id, @RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.create(id, request, userDetailsImpl.getUser());
    }

    @ApiOperation(value = "댓글 수정", notes = "해당 아이디의 댓글을 수정한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PatchMapping("/comments/{commentId}")
    public ResponseMessageDto update(@PathVariable(value = "commentId") Long id, @RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
       return commentService.update(id, request, userDetailsImpl.getUser());
    }

    @ApiOperation(value = "댓글 삭제", notes = "해당 아이디의 게시글에 댓글을 삭제한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @DeleteMapping("/comments/{commentId}")
    public ResponseMessageDto delete(@PathVariable(value = "commentId") Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.delete(id, userDetailsImpl.getUser());
    }
}
