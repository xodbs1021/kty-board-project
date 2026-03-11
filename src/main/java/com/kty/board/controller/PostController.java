package com.kty.board.controller;

import com.kty.board.domain.Comment;
import com.kty.board.domain.Post;
import com.kty.board.dto.PostCreateRequest;
import com.kty.board.dto.PostResponse;
import com.kty.board.repository.CommentRepository;
import com.kty.board.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller; // RestController에서 변경
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller // 화면 이동(redirect)을 위해 Controller 사용
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final CommentRepository commentRepository;

    /**
     * [화면용] 게시글 작성 처리
     * write.html의 <form action="/api/posts/write" method="post">와 연결
     */
    @PostMapping("/write")
    public String writePost(@RequestParam String title,
                            @RequestParam String content,
                            HttpSession session) {

        Long loginMemberId = (Long) session.getAttribute("loginMemberId");

        if (loginMemberId == null) {
            return "redirect:/";
        }

        postService.write(loginMemberId, title, content);
        return "redirect:/board"; // 저장 후 게시판 목록으로 이동
    }

    /**
     * [API] 게시글 목록 조회
     */
    @GetMapping
    @ResponseBody // JSON 반환을 위해 추가
    public List<PostResponse> getPosts() {
        return postService.findPosts().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * [API] 게시글 상세 조회
     */
    @GetMapping("/{postId}")
    @ResponseBody
    public PostResponse getPostDetail(@PathVariable Long postId) {
        Post post = postService.findOne(postId);
        List<Comment> comments = commentRepository.findByPostId(postId);
        return new PostResponse(post, comments);
    }

    /**
     * [API] 게시글 수정
     */
    @PatchMapping("/{postId}")
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable Long postId, @RequestBody PostCreateRequest request) {
        postService.updatePost(postId, request.getTitle(), request.getContent());
        return ResponseEntity.ok("게시글 수정 완료");
    }

    /**
     * [API] 게시글 삭제
     */
    @DeleteMapping("/{postId}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("게시글 삭제 완료");
    }
}