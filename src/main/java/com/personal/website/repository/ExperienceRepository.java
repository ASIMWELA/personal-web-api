package com.personal.website.repository;

import com.personal.website.entity.ExperienceEntity;
import com.personal.website.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<ExperienceEntity, Long>
{
    Optional<ExperienceEntity> findByName(String name);
    boolean existsByName(String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="DELETE FROM experience as e WHERE e.name=:expName AND e.user=:userEnt")
    void deleteExperience(@Param("expName") String expName, @Param("userEnt") UserEntity user);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="DELETE FROM experience as e WHERE e.user=:userEnt")
    void deleteExperience( @Param("userEnt") UserEntity user);
}
