package com.personal.website.repository;

import com.personal.website.entity.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillsRepository extends JpaRepository<SkillEntity, Long>
{
    Optional<SkillEntity> findByTechnology(String technology);
    boolean existsByTechnology(String technology);
}
