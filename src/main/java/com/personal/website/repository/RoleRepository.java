package com.personal.website.repository;

import com.personal.website.entity.RoleEntinty;
import com.personal.website.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntinty, Long>
{
    Optional<RoleEntinty>findByName(ERole name);
}
