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
    /**
     * 댓글 삭제 처리
     */
    @PostMapping("/comments/{commentId}/delete")
    public String deleteComment(@PathVariable("commentId") Long commentId,
                                @RequestParam("postId") Long postId,
                                HttpSession session) {
        // 1. 현재 로그인한 사용자 확인
        String loginNickname = (String) session.getAttribute("nickname");
        if (loginNickname == null) return "redirect:/";

        // 2. 서비스에서 댓글 찾기 및 본인 확인 후 삭제
        // (서비스 로직은 아래에서 구현)
        commentService.deleteComment(commentId, loginNickname);

        // 3. 다시 상세 페이지로 (조회수 안 오르는 /view 경로로!)
        return "redirect:/posts/" + postId + "/view";
    }

    /**
     * 댓글 수정 처리 (간단하게 내용만 변경)
     */
    @PostMapping("/comments/{commentId}/edit")
    public String updateComment(@PathVariable("commentId") Long commentId,
                                @RequestParam("postId") Long postId,
                                @RequestParam("content") String content,
                                HttpSession session) {
        String loginNickname = (String) session.getAttribute("nickname");
        if (loginNickname == null) return "redirect:/";

        commentService.updateComment(commentId, content, loginNickname);

        return "redirect:/posts/" + postId + "/view";
    }
}