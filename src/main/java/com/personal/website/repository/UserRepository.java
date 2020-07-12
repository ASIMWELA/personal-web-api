package com.personal.website.repository;

import com.personal.website.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByUserNameOrEmail(String username, String email);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    boolean existsByUserNameOrEmail(String userName, String Email);
}
