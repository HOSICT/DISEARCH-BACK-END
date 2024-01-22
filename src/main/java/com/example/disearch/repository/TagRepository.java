package com.example.disearch.repository;

import com.example.disearch.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    // 필요시 추가 메소드 작성 예정.
}