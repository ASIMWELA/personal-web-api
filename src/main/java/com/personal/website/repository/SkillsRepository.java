package com.personal.website.repository;

import com.personal.website.entity.SkillEntity;
import com.personal.website.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SkillsRepository extends JpaRepository<SkillEntity, Long>
{
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE skills AS s SET s.skills=:skill WHERE s.technology=:tech")
    void updateUpdateSkill(@Param("skill") String[] skill,  @Param("tech") String tech);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="DELETE FROM skills  where skills.technology=:tech AND skills.user=:newUser")
    void deleteSkill(@Param("tech") String tech, @Param("newUser")UserEntity user);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="DELETE FROM skills  where skills.user=:newUser")
    void deleteSkill(@Param("newUser")UserEntity user);

    Optional<SkillEntity> findByTechnology(String technology);
    boolean existsByTechnology(String technology);

}
