package com.hhgg.hhggbe.comment.service;

import com.hhgg.hhggbe.comment.dto.CommentDto;
import com.hhgg.hhggbe.comment.dto.CommentListDto;
import com.hhgg.hhggbe.comment.dto.CommentRequestDto;
import com.hhgg.hhggbe.comment.dto.ResponseMessageDto;
import com.hhgg.hhggbe.comment.entity.Comment;
import com.hhgg.hhggbe.comment.repository.CommentRepository;
import com.hhgg.hhggbe.post.entity.Post;
import com.hhgg.hhggbe.post.repository.PostRepository;
import com.hhgg.hhggbe.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public CommentListDto get(Long id) {
        //게시글 id 로 댓글 찾기 <- 없을 시 빈 리스트
        List<Comment> comments = commentRepository.findAllByPost_PostIdAndDeletedAtIsNull(id).orElse(new ArrayList<>());
        List<CommentDto> commentDto = comments.stream().map(CommentDto::new).collect(Collectors.toList());
        return new CommentListDto(commentDto);
    }

    @Transactional
    public ResponseMessageDto create(Long id, CommentRequestDto request, User user) {
        //댓글
        if(!request.isReply()) {
            Post post = postRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 아이디의 게시글이 존재하지 않습니다.")
            );
            commentRepository.save(new Comment(user, post, request.getComment(), request.isReply(), null));
        }

        //대댓글
        if(request.isReply()) {
            Comment comment = commentRepository.findByCommentIdAndDeletedAtIsNull(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 아이디의 댓글이 존재하지 않습니다.")
            );
            commentRepository.save(new Comment(user, comment.getPost(), request.getComment(), request.isReply(), comment.getCommentId()));
        }

        //리턴
        return new ResponseMessageDto(HttpStatus.CREATED.value(), "댓글 등록 완료");
    }

    @Transactional
    public ResponseMessageDto update(Long id, CommentRequestDto request, User user) {
        Comment comment = getComment(id, user);
        comment.update(request.getComment());
        return new ResponseMessageDto(HttpStatus.OK.value(), "댓글 수정 완료");
    }

    @Transactional
    public ResponseMessageDto delete(Long id, User user) {
        Comment comment = getComment(id, user);
        //대댓글 찾기
        List<Comment> replies = commentRepository.findByReferenceIdAndDeletedAtIsNull(comment.getCommentId()).orElse(new ArrayList<>());
        //대댓글 삭제 상태로 변경
        replies.forEach(Comment::commentDelete);
        //댓글 삭제 상태로 변경
        comment.commentDelete();
        return new ResponseMessageDto(HttpStatus.OK.value(), "댓글 삭제 완료");
    }

    private Comment getComment(Long id, User user) {
        //댓글의 존재 유무 확인
        Comment comment = commentRepository.findByCommentIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 댓글이 존재하지 않습니다.")
        );
        //작성자와 유저의 일치 여부 확인
        if(!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정 / 삭제 가능합니다");
        }
        //댓글 리턴
        return comment;
    }
}
