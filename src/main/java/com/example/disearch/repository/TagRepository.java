package com.example.disearch.repository;

import com.example.disearch.entity.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Transactional
    @Modifying
    @Query("update Tag t set t.count = t.count - 1 where t.name = :name and t.count > 0")
    void decrementCountByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("delete from Tag t where t.count = 0")
    void deleteTagsWithZeroCount();

    List<Tag> findTop15ByOrderByCountDesc();
}
