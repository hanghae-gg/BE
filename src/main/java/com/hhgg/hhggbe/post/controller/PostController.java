package com.hhgg.hhggbe.post.controller;

import com.hhgg.hhggbe.post.repository.PostRepository;
import com.hhgg.hhggbe.post.dto.PostRequestDto;
import com.hhgg.hhggbe.post.dto.PostResponseDto;
import com.hhgg.hhggbe.post.dto.ResponseDto;
import com.hhgg.hhggbe.post.service.PostService;
import com.hhgg.hhggbe.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @PostMapping(value = "/posts", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PostResponseDto> postCreate(@RequestPart PostRequestDto postRequestDto,
                                      @RequestPart(value = "imageUrl") MultipartFile imageUrl,
                                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {
        PostResponseDto postResponseDto = postService.createPost(postRequestDto, imageUrl, userDetailsImpl);
        return new ResponseEntity<>(postResponseDto, HttpStatus.CREATED);
    }

    // 게시글 한개만 불러오기
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> postRead(@PathVariable Long postId){
        PostResponseDto post = postService.readPost(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // 게시글 목록 불러오기
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> postsRead() {
        List<PostResponseDto> posts = postService.readPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // 게시글 수정하기
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> postPatch(@PathVariable Long postId,
                                     @RequestBody PostRequestDto postRequestDto,
                                     @RequestParam(value = "imageurl", required = false) MultipartFile imageUrl,
                                     @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws  IOException{
        PostResponseDto postResponseDto = postService.patchPost(postId, postRequestDto, imageUrl, userDetailsImpl);
        return new ResponseEntity<>(postResponseDto, HttpStatus.CREATED);
    }

    //게시글 삭제하기
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ResponseDto> postDelete(@PathVariable Long postId,
                                  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        ResponseDto responseDto = postService.deletePost(postId, userDetailsImpl);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
