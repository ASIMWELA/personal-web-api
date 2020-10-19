package com.personal.website.repository;

import com.personal.website.entity.EmploymentEntity;
import com.personal.website.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EmploymentRepository extends JpaRepository<EmploymentEntity, Long>
{
   boolean existsByCompany(String name);
   Optional<EmploymentEntity> findByCompany(String companyName);

   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value="DELETE FROM employment_entity AS e WHERE e.company =:compName AND e.user =:userEntity")
   void deleteEmpDetails(@Param("compName") String companyName, @Param("userEntity")UserEntity userEntity);

   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value="DELETE FROM employment_entity AS e WHERE e.user =:userEntity")
   void deleteEmpDetails(@Param("userEntity")UserEntity userEntity);


   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value="UPDATE employment_entity AS e SET e.company =:compName, e.accomplishments=:accompl, e.availability=:avail, e.duration=:dura WHERE e.company=:oldComName")
   void updateEmpInfo(@Param("compName") String newCompName, @Param("accompl")String [] newAccomplishment,@Param("avail") String newAvail, @Param("dura") String newDuration,@Param("oldComName") String oldCompName );
}
