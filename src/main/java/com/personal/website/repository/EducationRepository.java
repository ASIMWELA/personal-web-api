package com.personal.website.repository;

import com.personal.website.entity.EducationEntity;
import com.personal.website.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface EducationRepository extends JpaRepository<EducationEntity, Long>
{
    Optional<EducationEntity> findByInstitution(String institution);
    boolean existsByInstitution(String institution);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="DELETE FROM education as e where e.institution=:inst AND e.user=:user")
    void deleteEducationDetails(@Param("user")UserEntity user, @Param("inst") String institution);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="DELETE FROM education as e where e.user=:user")
    void deleteEducationDetails(@Param("user")UserEntity user);

}
