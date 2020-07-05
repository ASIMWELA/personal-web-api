package com.personal.website.repository;

import com.personal.website.entity.ContactInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInfoRepository extends JpaRepository<ContactInfoEntity, Long>
{
}
