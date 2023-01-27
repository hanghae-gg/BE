package com.hhgg.hhggbe.post.controller;

import com.hhgg.hhggbe.post.dto.PostRequestDto;
import com.hhgg.hhggbe.post.dto.PostResponseDto;
import com.hhgg.hhggbe.post.service.PostService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("posts");  // 이렇게 하면 되나 ??
        return mv; // view + data pathVariable
    }
    @PostMapping("/posts")
    public PostResponseDto postCreate(@RequestBody PostRequestDto postRequestDto,
                                      @RequestParam(value = "imageUrl", required = false)MultipartFile imageUrl,
                                      @AuthenticationPrincipal UserDetailsIpml userDetailsIpml) throws IOException {
        return postService.createPost(postRequestDto, imageUrl, userDetailsIpml);
    }

    @GetMapping("/posts")
    public List<PostResponseDto> postsRead() {
        return postService.readPosts();
    }
}
