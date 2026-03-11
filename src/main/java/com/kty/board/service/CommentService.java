package com.kty.board.service;

import com.kty.board.domain.Comment;
import com.kty.board.domain.Member;
import com.kty.board.domain.Post;
import com.kty.board.repository.CommentRepository;
import com.kty.board.repository.MemberRepository;
import com.kty.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 댓글 작성 (이 메서드 하나로 통합합니다)
     */
    @Transactional
    public Long writeComment(Long postId, Long memberId, String content) {
        // 1. 게시글 조회 (없으면 에러)
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // 2. 작성자 조회 (없으면 에러)
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        // 3. 댓글 생성 및 저장
        Comment comment = Comment.builder()
                .content(content)
                .post(post)
                .member(member)
                .build();

        commentRepository.save(comment);
        return comment.getId();
    }
}