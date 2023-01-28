package com.hhgg.hhggbe.post.controller;

import com.hhgg.hhggbe.post.dto.ResponseDataDto;
import com.hhgg.hhggbe.post.entity.Post;
import com.hhgg.hhggbe.post.repository.PostRepository;
import com.hhgg.hhggbe.post.dto.PostRequestDto;
import com.hhgg.hhggbe.post.dto.PostResponseDto;
import com.hhgg.hhggbe.post.dto.ResponseDto;
import com.hhgg.hhggbe.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("posts");  // 이렇게 하면 되나 ??
        return mv; // view + data pathVariable
    }

    // 게시글 작성하기
    @PostMapping("/posts")
    public ResponseEntity<ResponseDataDto> postCreate(@RequestBody PostRequestDto postRequestDto,
                                      @RequestParam(value = "imageUrl", required = false)MultipartFile imageUrl,
                                      @AuthenticationPrincipal UserDetailsIpml userDetailsIpml) throws IOException {
        PostResponseDto postResponseDto = postService.createPost(postRequestDto, imageUrl, userDetailsIpml);
        ResponseDataDto dataDto = new ResponseDataDto(201, "게시글이 성공적으로 작성되었습니다.", postResponseDto);
        return new ResponseEntity<>(dataDto, HttpStatus.CREATED);
    }

    // 게시글 한개만 불러오기
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ResponseDataDto> postRead(@PathVariable Long postId){
        PostResponseDto post = postService.readPost(postId);
        ResponseDataDto dataDto = new ResponseDataDto(200, "게시글 조회 성공", post);
        return new ResponseEntity<>(dataDto, HttpStatus.OK);
    }

    // 게시글 목록 불러오기
    @GetMapping("/posts")
    public ResponseEntity<ResponseDataDto> postsRead() {
        List<PostResponseDto> posts = postService.readPosts();
        ResponseDataDto dataDto = new ResponseDataDto(200, "전체 게시글 목록 조회", posts);
        return new ResponseEntity<>(dataDto, HttpStatus.OK);
    }

    // 게시글 수정하기
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<ResponseDataDto> postPatch(@PathVariable Long postId,
                                     @RequestBody PostRequestDto postRequestDto,
                                     @RequestParam(value = "imageurl", required = false) MultipartFile imageUrl,
                                     @AuthenticationPrincipal UserDetailsImpl userDetailsimpl) throws  IOException{
        PostResponseDto postResponseDto = postService.patchPost(postId, postRequestDto, imageUrl, userDetailsimpl);
        ResponseDataDto dataDto = new ResponseDataDto(201, "게시글이 성공적으로 수정되었습니다.", postResponseDto);
        return new ResponseEntity<>(dataDto, HttpStatus.CREATED);
    }

    //게시글 삭제하기
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ResponseDataDto> postDelete(@PathVariable Long postId,
                                  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        ResponseDto responseDto = postService.deletePost(postId, userDetailsImpl);
        ResponseDataDto dataDto = new ResponseDataDto(201, "게시글이 성공적으로 삭제되었습니다.",responseDto);
        return new ResponseEntity<>(dataDto, HttpStatus.OK);
    }

}
