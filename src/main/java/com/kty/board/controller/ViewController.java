package com.kty.board.controller;

import com.kty.board.dto.PostResponse;
import com.kty.board.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final PostService postService;

    /**
     * 1. 첫 접속 시 로그인 페이지로 이동
     */
    @GetMapping("/")
    public String loginPage(HttpSession session) {
        // 이미 로그인된 사용자가 접속하면 바로 게시판으로 보냄
        if (session.getAttribute("loginMemberId") != null) {
            return "redirect:/board";
        }
        return "login";
    }

    /**
     * 2. 회원가입 페이지 이동
     */
    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    /**
     * 3. 게시판 목록 페이지
     */
    @GetMapping("/board")
    public String boardPage(Model model, HttpSession session) {
        // 보안: 미로그인 사용자 차단
        if (session.getAttribute("loginMemberId") == null) {
            return "redirect:/";
        }

        // 서비스에서 가져온 Post 엔티티 리스트를 PostResponse DTO 리스트로 변환해서 전달
        List<PostResponse> posts = postService.findPosts().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());

        model.addAttribute("posts", posts);
        return "board";
    }

    /**
     * 4. 글쓰기 페이지 이동
     */
    @GetMapping("/write")
    public String writePage(HttpSession session) {
        if (session.getAttribute("loginMemberId") == null) {
            return "redirect:/";
        }
        return "write";
    }

    /**
     * 5. 게시글 상세 페이지 이동 (추후 구현용 미리 추가)
     * 이 주소는 board.html에서 th:href="@{/posts/{id}(id=${post.id})}"와 연결됩니다.
     */
    @GetMapping("/posts/{postId}")
    public String detailPage(@PathVariable("postId") Long postId, Model model, HttpSession session) {
        if (session.getAttribute("loginMemberId") == null) {
            return "redirect:/";
        }

        // 상세 조회를 하면 PostService에서 조회수 증가 로직이 실행됩니다.
        model.addAttribute("post", new PostResponse(postService.findOne(postId)));
        return "detail";
    }
}