package com.sparta.instahub.domain.post.repository;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID>,PostRepositoryCustom {
    Page<Post> findByUserIn(List<User> users, Pageable pageable);

    Page<Post> findByUserInAndUserUsernameContainingIgnoreCase(List<User> users, String username, Pageable pageable);
}
