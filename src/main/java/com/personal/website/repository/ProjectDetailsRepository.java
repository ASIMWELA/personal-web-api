package com.personal.website.repository;

import com.personal.website.entity.ProjectDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectDetailsRepository extends JpaRepository<ProjectDetailsEntity, Long>
{
    Optional<ProjectDetailsEntity> findByName(String name);
    boolean existsByName(String name);
}
