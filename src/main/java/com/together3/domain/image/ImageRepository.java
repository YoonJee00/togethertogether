package com.together3.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query(value = "SELECT * FROM image WHERE user_id in (SELECT to_user_id FROM subscribe WHERE from_user_id = :principalId) ORDER BY id DESC", nativeQuery = true)
    Page<Image> mStory(int principalId, Pageable pageable);

}
