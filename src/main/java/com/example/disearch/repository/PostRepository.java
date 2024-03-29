package com.example.disearch.repository;

import com.example.disearch.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserIdOrderByCreatedAtDesc(String userId);

    Page<Post> findAllByTagsName(String tagName, Pageable pageable);

    Page<Post> findAllByCategory(String category, Pageable pageable);

    Optional<Post> findByServerId(String serverId);

    Optional<Post> findByIdAndUserId(Long id, String userId);

}