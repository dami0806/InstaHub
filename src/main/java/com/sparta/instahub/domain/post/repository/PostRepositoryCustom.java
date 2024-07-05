package com.sparta.instahub.domain.post.repository;


import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.entity.SearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    Page<Post> findAllBySearchCond(SearchCond searchCond, Pageable pageable);
}
