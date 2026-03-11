package com.kty.board.service;

import com.kty.board.domain.Member;
import com.kty.board.domain.Post;
import com.kty.board.repository.MemberRepository;
import com.kty.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 작성
     */
    @Transactional
    public Long write(Long memberId, String title, String content) {
        // 1. 작성자(Member) 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. 게시글 객체 생성 (빌더 패턴)
        Post post = Post.builder()
                .title(title)
                .content(content)
                .member(member) // 연관관계 설정
                .build();

        // 3. 저장
        postRepository.save(post);
        return post.getId();
    }

    /**
     * 전체 게시글 조회
     */
    public List<Post> findPosts() {
        return postRepository.findAll();
    }
}