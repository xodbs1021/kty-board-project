package com.kty.board.controller;

import com.kty.board.domain.Member;
import com.kty.board.dto.MemberJoinRequest;
import com.kty.board.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 형태로 데이터를 주고받는 API 컨트롤러
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

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
}