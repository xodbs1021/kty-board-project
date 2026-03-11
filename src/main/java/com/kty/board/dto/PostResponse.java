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
    private String nickname; // board.html의 th:text="${post.nickname}"과 맞춤
    private int viewCount;
    private List<CommentResponse> comments;

    // 1. 목록 조회용 생성자 (게시판 리스트 출력 시 사용)
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getMember().getNickname();
        this.viewCount = post.getViewCount();
    }

    // 2. 상세 조회용 생성자 (댓글 리스트 포함 시 사용)
    public PostResponse(Post post, List<Comment> comments) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getMember().getNickname();
        this.viewCount = post.getViewCount();
        this.comments = comments.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
}