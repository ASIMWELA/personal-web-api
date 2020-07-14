package com.personal.website.repository;

import com.personal.website.entity.ExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<ExperienceEntity, Long>
{
    Optional<ExperienceEntity> findByName(String name);
    boolean existsByName(String name);
}
