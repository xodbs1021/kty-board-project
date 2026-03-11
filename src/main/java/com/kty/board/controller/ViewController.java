package com.kty.board.controller;

import com.kty.board.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final PostService postService;

    // 1. 첫 접속 시 로그인 페이지로
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    // 2. 게시판 목록 페이지 (세션 체크 로직 통합)
    @GetMapping("/board")
    public String boardPage(Model model, HttpSession session) {
        // 로그인 안 한 사용자는 로그인 페이지로 리다이렉트 (보안)
        if (session.getAttribute("loginMemberId") == null) {
            return "redirect:/";
        }

        // 게시글 목록을 가져와서 화면에 전달
        model.addAttribute("posts", postService.findPosts());
        return "board";
    }

    // 3. 글쓰기 페이지 페이지
    @GetMapping("/write")
    public String writePage(HttpSession session) {
        // 글쓰기도 로그인한 사람만 가능하게 보안 추가
        if (session.getAttribute("loginMemberId") == null) {
            return "redirect:/";
        }
        return "write";
    }
}