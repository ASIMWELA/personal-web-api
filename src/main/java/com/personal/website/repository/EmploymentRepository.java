package com.personal.website.repository;

import com.personal.website.entity.EmploymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentRepository extends JpaRepository<EmploymentEntity, Long>
{
   boolean existsByCompany(String name);
}
