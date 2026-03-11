package com.kty.board.controller;

import com.kty.board.dto.PostCreateRequest;
import com.kty.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public Long createPost(@RequestBody PostCreateRequest request) {
        return postService.write(request.getMemberId(), request.getTitle(), request.getContent());
    }
    @GetMapping
    public List<PostResponse> getPosts() {
        return postService.findPosts().stream()
                .map(PostResponse::new) // 엔티티 리스트를 DTO 리스트로 변환
                .collect(Collectors.toList());
    }
}