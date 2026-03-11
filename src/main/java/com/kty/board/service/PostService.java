package com.kty.board.service;

import com.kty.board.domain.Member;
import com.kty.board.domain.Post;
import com.kty.board.repository.MemberRepository;
import com.kty.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List; // 👈 1. util 패키지 확인

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public List<Post> findPosts() {
        // 2. 이제 Incompatible types 에러가 사라집니다.
        return postRepository.findAllByOrderByIdDesc();
    }

    @Transactional
    public Long write(Long memberId, String title, String content) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Post post = Post.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();

        return postRepository.save(post).getId();
    }

    @Transactional
    public Post findOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.addViewCount();
        return post;
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }
}