package com.sparta.instahub.s3.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import com.sparta.instahub.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuid2")
    private UUID id = UlidCreator.getMonotonicUlid().toUuid();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private  String url;

    public Image(String name, String url){
        this.name = name;
        this.url = url;
    }
}
