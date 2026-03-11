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
    // PostService.java 안에 추가

    /**
     * 게시글 수정
     */
    @Transactional
    public void updatePost(Long postId, String title, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 변경 감지 기능을 이용하여 엔티티 정보 수정
        post.update(title, content);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        postRepository.delete(post);
    }

    /**
     * 전체 게시글 조회
     */
    public List<Post> findPosts() {
        return postRepository.findAll();
    }
    // PostService.java에 추가
    public Post findOne(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }
}