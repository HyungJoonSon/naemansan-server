package com.dongguk.cse.naemansan.repository;

import com.dongguk.cse.naemansan.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query(value = "SELECT t FROM Tag t WHERE t.id IN :list")
    List<Tag> findTagsByIds(@Param("list") Collection<Long> tagIds);
}
