package com.hhgg.hhggbe.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hhgg.hhggbe.post.Post;
import com.hhgg.hhggbe.post.PostRepository;
import com.hhgg.hhggbe.post.dto.PostRequestDto;
import com.hhgg.hhggbe.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private String S3Bucket = "";  // bucket이름

    @Autowired
    AmazonS3Client amazonS3Client;

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
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }
}
