package com.sparta.instahub.domain.follow.repository;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, UUID>,FollowRepositoryCustom {
    boolean existsByFollowerAndFollowing(User follower, User following); // //관계확인은 단순한 jpa가 적합하다고 판단
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    List<Follow> findByFollower(User follower);
//    List<Follow> findByFollowing(User following);

}
