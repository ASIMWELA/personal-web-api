package com.personal.website.repository;

import com.personal.website.entity.ProfilePictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePictureEntity, Long>
{
}
