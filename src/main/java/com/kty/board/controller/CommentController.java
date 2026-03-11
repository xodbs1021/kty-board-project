package com.kty.board.controller;

import com.kty.board.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 등록 처리
     * detail.html의 <form action="/api/comments" method="post">와 연결됩니다.
     */
    @PostMapping
    public String writeComment(@RequestParam("postId") Long postId,
                               @RequestParam("content") String content,
                               HttpSession session) {

        // 1. 세션에서 로그인한 사용자 ID 가져오기
        Long loginMemberId = (Long) session.getAttribute("loginMemberId");

        // 2. 비로그인 사용자는 로그인 페이지로 리다이렉트
        if (loginMemberId == null) {
            return "redirect:/";
        }

        // 3. 댓글 저장 서비스 호출 (메서드 명: writeComment 확인)
        commentService.writeComment(postId, loginMemberId, content);

        // 4. 완료 후 해당 게시글 상세 페이지로 리다이렉트
        // 주의: 앞에 /를 붙여야 /api/comments/posts/1이 아닌 /posts/1로 이동합니다.
        return "redirect:/posts/" + postId;
    }
}