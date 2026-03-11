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
@Transactional(readOnly = true) // 기본적으로 조회용으로 설정 (성능 최적화)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 작성
     */
    @Transactional
    public Long write(Long memberId, String title, String content) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Post post = Post.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();

        postRepository.save(post);
        return post.getId();
    }

    /**
     * 게시글 수정 (변경 감지 활용)
     */
    @Transactional
    public void updatePost(Long postId, String title, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

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
     * 단건 조회 (조회수 증가 포함)
     */
    @Transactional // 조회가 아닌 '수정(조회수)'을 포함하므로 @Transactional을 별도로 붙임
    public Post findOne(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        post.addViewCount(); // 조회수 증가 (Dirty Checking)
        return post;
    }

    /**
     * 전체 게시글 조회
     */
    public List<Post> findPosts() {
        return postRepository.findAll();
    }
}