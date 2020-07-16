package com.personal.website.repository;

import com.personal.website.entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EducationRepository extends JpaRepository<EducationEntity, Long>
{
    Optional<EducationEntity> findByInstitution(String institution);
    boolean existsByInstitution(String institution);
}
