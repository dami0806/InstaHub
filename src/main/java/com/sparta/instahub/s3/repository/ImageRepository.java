package com.sparta.instahub.s3.repository;

import com.sparta.instahub.s3.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    Image findByUrl(String url);
}
