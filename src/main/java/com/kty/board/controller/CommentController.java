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

        Long loginMemberId = (Long) session.getAttribute("loginMemberId");
        if (loginMemberId == null) return "redirect:/";

        commentService.writeComment(postId, loginMemberId, content);

        // /view가 붙은 주소로 보내서 조회수 증가를 막습니다.
        return "redirect:/posts/" + postId + "/view";
    }
}