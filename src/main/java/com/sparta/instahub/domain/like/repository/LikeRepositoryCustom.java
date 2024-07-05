package com.sparta.instahub.domain.like.repository;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeRepositoryCustom {
    Page<Post> findLikedPosts(User user, Pageable pageable);
    Page<Comment> findLikedComments(User user, Pageable pageable);
}
