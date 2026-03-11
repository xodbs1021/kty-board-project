package com.kty.board.repository;

import com.kty.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본적인 CRUD(저장, 조회, 수정, 삭제)는 이미 포함되어 있습니다.
}