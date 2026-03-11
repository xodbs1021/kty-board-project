package com.kty.board.controller;

import com.kty.board.domain.Member;
import com.kty.board.dto.MemberJoinRequest;
import com.kty.board.repository.MemberRepository;
import com.kty.board.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
@RequiredArgsConstructor
@RestController // JSON 형태로 데이터를 주고받는 API 컨트롤러
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody @Valid MemberJoinRequest request) {
        // DTO를 엔티티로 변환 (객체지향적 설계: 생성은 엔티티가 책임짐)
        Member member = Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .build();

        memberService.join(member);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        // 1. 이메일로 회원 조회 (MemberRepository에 findByEmail 메서드가 필요합니다)
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        // 2. 비밀번호 체크 (간단하게 비교)
        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 로그인 성공 시 세션에 회원 ID 저장
        session.setAttribute("loginMemberId", member.getId());
        session.setAttribute("nickname", member.getNickname());

        return "redirect:/board"; // 로그인 성공 시 게시판으로 이동
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화 (모든 정보 삭제)
        return "redirect:/";
    }
}