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

    @PostMapping
    public String writeComment(@RequestParam Long postId,
                               @RequestParam String content,
                               HttpSession session) {

        Long loginMemberId = (Long) session.getAttribute("loginMemberId");

        if (loginMemberId == null) {
            return "redirect:/";
        }

        // 댓글 저장 서비스 호출
        commentService.writeComment(postId, loginMemberId, content);

        // 중요: 댓글을 단 후 다시 해당 게시글 상세 페이지로 돌아갑니다.
        return "redirect:/posts/" + postId;
    }
}