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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final CommentRepository commentRepository;

    /**
     * [화면용] 게시글 작성 처리
     */
    @PostMapping("/write")
    public String writePost(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            HttpSession session) {

        Object memberIdObj = session.getAttribute("loginMemberId");

        if (memberIdObj == null) {
            return "redirect:/"; // 로그인 안됐으면 로그인 페이지로
        }

        Long loginMemberId = (Long) memberIdObj;
        postService.write(loginMemberId, title, content);

        // 중요: 앞에 /를 붙여서 /api/posts/board가 아닌 /board로 가게 합니다.
        return "redirect:/board";
    }

    /**
     * [API] 게시글 목록 조회
     */
    @GetMapping
    @ResponseBody
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
    public PostResponse getPostDetail(@PathVariable("postId") Long postId) {
        Post post = postService.findOne(postId);
        List<Comment> comments = commentRepository.findByPostId(postId);
        return new PostResponse(post, comments);
    }

    /**
     * [API] 게시글 수정
     */
    @PatchMapping("/{postId}")
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable("postId") Long postId, @RequestBody PostCreateRequest request) {
        postService.updatePost(postId, request.getTitle(), request.getContent());
        return ResponseEntity.ok("게시글 수정 완료");
    }

    /**
     * [API] 게시글 삭제
     */
    @DeleteMapping("/{postId}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("게시글 삭제 완료");
    }
}