//package com.sparta.instahub.domain.follow.entity;
//
//import com.github.f4b6a3.ulid.UlidCreator;
//import com.sparta.instahub.common.entity.BaseEntity;
//import com.sparta.instahub.domain.auth.entity.User;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.NoArgsConstructor;
//
//import java.util.UUID;
//
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Follow extends BaseEntity {
//
//    @Id
//    @Column(columnDefinition = "BINARY(16)")
//    @GeneratedValue(generator = "uuid2")
//    private UUID id = UlidCreator.getMonotonicUlid().toUuid();
//
//    @ManyToOne
//    @JoinColumn(name = "follower_id")
//    private User follower;
//
//    @ManyToOne
//    @JoinColumn(name = "follower_id")
//    private User follwee;
//
//    @Builder
//    public Follow(User follower, User follwee) {
//        this.follower = follower;
//        this.follwee = follwee;
//    }
//}
