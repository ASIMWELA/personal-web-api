package com.personal.website.repository;

import com.personal.website.entity.ProjectDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProjectDetailsRepository extends JpaRepository<ProjectDetailsEntity, Long>
{
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE project AS p SET p.name=:newName, p.description=:newDecs, p.role=:newRole,p.collaborators=:newCol, p.locationLink=:newLink WHERE p.name=:pName")
    void updateUpdateProject(
            @Param("newName")String name,
            @Param("newDecs")String newDecs,
            @Param("newRole")String role,
            @Param("newCol") String[] newCol,
            @Param("newLink") String newLink,
            @Param("pName") String pName);

    Optional<ProjectDetailsEntity> findByName(String name);
    boolean existsByName(String name);
}
