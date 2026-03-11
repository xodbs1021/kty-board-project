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

    @Transactional
    public Long writeComment(Long postId, Long memberId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(content)
                .post(post)
                .member(member)
                .build();

        commentRepository.save(comment);
        return comment.getId();
    }
}