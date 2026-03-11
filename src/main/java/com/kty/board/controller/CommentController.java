package com.kty.board.controller;

import com.kty.board.dto.CommentRequest;
import com.kty.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Long createComment(@RequestBody CommentRequest request) {
        return commentService.writeComment(
                request.getPostId(),
                request.getMemberId(),
                request.getContent()
        );
    }
}