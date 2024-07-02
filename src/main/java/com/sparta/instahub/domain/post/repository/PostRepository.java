package com.sparta.instahub.domain.post.repository;

import com.sparta.instahub.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
