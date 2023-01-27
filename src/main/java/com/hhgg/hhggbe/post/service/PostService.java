package com.hhgg.hhggbe.post.service;

import com.amazonaws.Response;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hhgg.hhggbe.comment.entity.Comment;
import com.hhgg.hhggbe.post.dto.ResponseDataDto;
import com.hhgg.hhggbe.post.entity.Post;
import com.hhgg.hhggbe.post.repository.PostRepository;
import com.hhgg.hhggbe.post.dto.PostRequestDto;
import com.hhgg.hhggbe.post.dto.PostResponseDto;
import com.hhgg.hhggbe.post.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private String S3Bucket = "";  // bucket이름

    @Autowired
    AmazonS3Client amazonS3Client;


    //게시물 작성
    public PostResponseDto createPost(PostRequestDto postRequestDto,
                                      MultipartFile imageUrl,
                                      UserDetailsIpml userDetailsIpml) throws IOException {
        String imageUrlString;

        if (imageUrl != null) {
            if (!imageUrl.getOriginalFilename().equals("")) {
                String originName = UUID.randomUUID().toString();
                long size = imageUrl.getSize();

                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(imageUrl.getContentType());
                objectMetadata.setContentLength(size);

                amazonS3Client.putObject(
                        new PutObjectRequest(S3Bucket, originName, imageUrl.getInputStream(), objectMetadata )
                                .withCannedAcl(CannedAccessControlList.PublicRead)
                );
                imageUrlString = amazonS3Client.getUrl(S3Bucket, originName).toString();
            }
            else {
                imageUrlString = "";
            }
        }else {
            imageUrlString = "";
        }
        Post post = new Post(postRequestDto, imageUrlString, userDetailsIpml.getUser());
        postRepository.save(post);
        return new PostResponseDto(post);
    }


    // 게시물 모두 불러오기
    public List<PostResponseDto> readPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : posts) {
            if (post.isDelete()) {
                continue;
            }
            PostResponseDto postResponseDto = new PostResponseDto(post);
            postResponseDtos.add(postResponseDto);
        }
        return postResponseDtos;
    }


    //특정 게시물 불러오기
    public ResponseEntity<ResponseDataDto> readPost(Long postId){
        Optional<Post> post = postRepository.findByPostId(postId);

        if (post.get().isDelete()){
            throw new IllegalArgumentException("이미 삭제된 게시물입니다.");
        }
        post.get().PostVisit();
        postRepository.save(post.get());  // post를 불러오기 전에 visit를 증가시키고 저장
//        return new PostResponseDto(post.get());
    }


    // 게시물 수정하기
    @Transactional
    public PostResponseDto patchPost(Long postId, PostRequestDto postRequestDto,
                                     MultipartFile imageUrl,
                                     UserDetailsImpl userDetailsImpl) throws IOException{
        Optional<Post> post = postRepository.findByPostId(postId);
        //삭제된 게시물인지 확인
        if (post.get().isDelete()){
            throw new IllegalArgumentException("이미 삭제된 게시물입니다.");
        }
        //본인이 작성한 게시물인지 확인
        if (!post.get().getUser().getId().equals(userDetailsImpl.getUser().getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if (imageUrl != null) {
            if (!imageUrl.getOriginalFilename().equals("")){
                String originName = UUID.randomUUID().toString();
                long size = imageUrl.getSize();
                // 사진파일 형식과 크기 가져오기
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(imageUrl.getContentType());
                objectMetadata.setContentLength(size);
                //S3에 저장하기
                amazonS3Client.putObject(
                        new PutObjectRequest(S3Bucket, originName, imageUrl.getInputStream(), objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead)
                );
                // 접근 가능한 URL 가져오기
                String imageUrlString = amazonS3Client.getUrl(S3Bucket, originName).toString();
                post.get().PostPatch(postRequestDto, imageUrlString);
            } else {
                post.get().PostPatchNoImage(postRequestDto);
            }
        }else {
            post.get().PostPatchNoImage(postRequestDto);
        }

        return new PostResponseDto(post.get());
    }


    // 게시글 삭제하기
    @Transactional
    public ResponseDto deletePost(Long postId, UserDetailsImpl userDetailsImpl) {
        Optional<Post> post = postRepository.findByPostId(postId);
        if (!post.get().isDelete()) {
            post.get().PostDelete();
            List<Comment> comments = post.get().getComments();
            for (Comment comment : comments) {
                comment.commentDelete();  // 이렇게 하면 되나요?
            }
        } else {
            throw new IllegalArgumentException("이미 삭제된 게시물입니다.");
        }
        if (!post.get().getUser().getId().equals(userDetailsImpl.getUser().getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        post.get().PostDelete();
        ResponseDto responseDto = new ResponseDto();
        responseDto.ResponseTrue();
        return responseDto;
    }
}
