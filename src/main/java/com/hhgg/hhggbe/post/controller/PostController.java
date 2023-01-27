package com.hhgg.hhggbe.post.controller;

import com.hhgg.hhggbe.post.repository.PostRepository;
import com.hhgg.hhggbe.post.dto.PostRequestDto;
import com.hhgg.hhggbe.post.dto.PostResponseDto;
import com.hhgg.hhggbe.post.dto.ResponseDto;
import com.hhgg.hhggbe.post.service.PostService;
import lombok.RequiredArgsConstructor;
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
    public PostResponseDto postCreate(@RequestBody PostRequestDto postRequestDto,
                                      @RequestParam(value = "imageUrl", required = false)MultipartFile imageUrl,
                                      @AuthenticationPrincipal UserDetailsIpml userDetailsIpml) throws IOException {
        return postService.createPost(postRequestDto, imageUrl, userDetailsIpml);
    }

    // 게시글 한개만 불러오기
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> postRead(@PathVariable Long postId){
        return postService.readPost(postId);
    }

    // 게시글 목록 불러오기
    @GetMapping("/posts")
    public List<PostResponseDto> postsRead() {
        return postService.readPosts();
    }

    // 게시글 수정하기
    @PatchMapping("/posts/{postId}")
    public PostResponseDto postPatch(@PathVariable Long postId,
                                     @RequestBody PostRequestDto postRequestDto,
                                     @RequestParam(value = "imageurl", required = false) MultipartFile imageUrl,
                                     @AuthenticationPrincipal UserDetailsImpl userDetailsimpl) throws  IOException{
        return postService.patchPost(postId, postRequestDto, imageUrl, userDetailsimpl);
    }

    //게시글 삭제하기
    @DeleteMapping("/posts/{postId}")
    public ResponseDto postDelete(@PathVariable Long postId,
                                  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return postService.deletePost(postId, userDetailsImpl);
    }

}
