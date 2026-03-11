package com.kty.board.dto;

import com.kty.board.domain.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private String authorNickname; // 댓글 작성자 닉네임

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.authorNickname = comment.getMember().getNickname();
    }
}