package com.example.disearch.repository;

import com.example.disearch.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByTagsName(@Param("tagName") String tagName);
    List<Post> findAllByCategory(String category);

}