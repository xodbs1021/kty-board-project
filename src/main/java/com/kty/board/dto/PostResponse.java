package com.kty.board.dto;

import com.kty.board.domain.Post;
import lombok.Getter;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String authorNickname; // 작성자 닉네임 추가!

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorNickname = post.getMember().getNickname(); // 연관된 객체에서 가져옴
    }
}