package com.kty.board.controller;

import com.kty.board.domain.Member;
import com.kty.board.repository.MemberRepository;
import com.kty.board.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller; // RestController 대신 Controller 사용
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // 화면 이동(redirect)을 위해 Controller로 변경
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // 회원가입 처리
    @PostMapping("/join")
    public String join(@RequestParam String email,
                       @RequestParam String nickname,
                       @RequestParam String password,
                       Model model) {

        try {
            Member member = Member.builder()
                    .email(email)
                    .nickname(nickname)
                    .password(password)
                    .build();

            memberService.join(member);
            return "redirect:/";

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            // 가입 실패 시 입력했던 값을 다시 모델에 담아줍니다 (비밀번호는 보안상 제외)
            model.addAttribute("email", email);
            model.addAttribute("nickname", nickname);
            return "join";
        }
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session) {

        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        // 비밀번호 체크
        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 세션에 로그인 정보 저장
        session.setAttribute("loginMemberId", member.getId());
        session.setAttribute("nickname", member.getNickname());

        return "redirect:/board"; // 로그인 성공 시 게시판 목록으로 이동
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}