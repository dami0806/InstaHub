package com.sparta.instahub.domain.follow.repository;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, UUID> {
    boolean existsByFollowerAndFollowing(User follower, User following);
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    List<Follow> findByFollower(User follower);
    List<Follow> findByFollowing(User following);

    @Query("SELECT f.following, COUNT(f) as followerCount " +
            "FROM Follow f " +
            "GROUP BY f.following " +
            "ORDER BY followerCount DESC")
    List<Object[]> findTop10ByFollowerCount();
}
