package com.sparta.instahub.domain.post.repository;


import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.entity.SearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepositoryCustom {
    Page<Post> findAllBySearchCond(SearchCond searchCond, Pageable pageable);
    long countLikesByPostId(UUID postId);
}
