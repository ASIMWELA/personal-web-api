package com.personal.website.repository;

import com.personal.website.entity.SubscriberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<SubscriberEntity, Long>
{
    Optional<SubscriberEntity>findByEmail(String email);
    boolean existsByEmail(String email);
}
