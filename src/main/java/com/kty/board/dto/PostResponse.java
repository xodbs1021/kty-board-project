package com.kty.board.dto;

import com.kty.board.domain.Comment;
import com.kty.board.domain.Post;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String authorNickname;
    private List<CommentResponse> comments;

    // 1. 목록 조회용 생성자 (게시글 정보만!)
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorNickname = post.getMember().getNickname();
    }

    // 2. 상세 조회용 생성자 (댓글 리스트 포함!)
    public PostResponse(Post post, List<Comment> comments) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorNickname = post.getMember().getNickname();
        this.comments = comments.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
}