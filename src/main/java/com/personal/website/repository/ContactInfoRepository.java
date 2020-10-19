package com.personal.website.repository;

import com.personal.website.entity.ContactInfoEntity;
import com.personal.website.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfoEntity, Long>
{

    Optional<ContactInfoEntity> findByPhoneNumber(String phoneNumber);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE contact_info AS c SET c.city=:nCity, c.physicalAddress=:nAdr, c.phoneNumber=:nNumber,c.country=:nCountry WHERE c.phoneNumber=:orNumber")
    void updateContactInfo(@Param("nCity") String city,  @Param("nAdr") String address, @Param("nNumber") String phone, @Param("nCountry") String country, @Param("orNumber") String originalPhone);

}
