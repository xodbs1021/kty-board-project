package com.kty.board.controller;

import com.kty.board.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // RestController가 아님에 주의!
@RequiredArgsConstructor
public class ViewController {

    private final PostService postService;

    @GetMapping("/") // 첫 접속 시 로그인 페이지로
    public String loginPage() {
        return "login"; // templates/login.html을 엽니다.
    }

    @GetMapping("/board")
    public String boardPage(Model model) {
        model.addAttribute("posts", postService.findPosts()); // 게시글 목록을 화면에 전달
        return "board";
    }

    @GetMapping("/write")
    public String writePage() {
        return "write";
    }
    @GetMapping("/board")
    public String boardPage(Model model, HttpSession session) {
        // 1. 로그인 안 한 사용자는 로그인 페이지로 튕구기 (보안)
        if (session.getAttribute("loginMemberId") == null) {
            return "redirect:/";
        }

        // 2. 게시글 목록 가져와서 모델에 담기
        // PostService에서 만든 목록 조회 메서드를 활용합니다.
        model.addAttribute("posts", postService.findPosts());

        return "board";
    }
}