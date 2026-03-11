package com.kty.board.controller;

import com.kty.board.dto.PostCreateRequest;
import com.kty.board.dto.PostResponse;
import com.kty.board.repository.CommentRepository;
import com.kty.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.kty.board.domain.Post;
import com.kty.board.domain.Comment;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final CommentRepository commentRepository;

    @PostMapping
    public Long createPost(@RequestBody PostCreateRequest request) {
        return postService.write(request.getMemberId(), request.getTitle(), request.getContent());
    }
    @GetMapping
    public List<PostResponse> getPosts() {
        return postService.findPosts().stream()
                .map(post -> new PostResponse(post)) // 이 부분을 람다식으로 변경!
                .collect(Collectors.toList());
    }
    @GetMapping("/{postId}")
    public PostResponse getPostDetail(@PathVariable Long postId) {
        // 1. 게시글 조회
        Post post = postService.findOne(postId);

        // 2. 해당 게시글의 댓글들 조회 (CommentRepository에 만들어둔 기능 활용)
        List<Comment> comments = commentRepository.findByPostId(postId);

        return new PostResponse(post, comments);
    }
}