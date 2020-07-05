package com.personal.website.service;

import com.personal.website.entity.ContactInfoEntity;
import com.personal.website.repository.ContactInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoService
{

    @Autowired
    private ContactInfoRepository contactInfoRepository;
    public ContactInfoEntity save(ContactInfoEntity contactInfoEntity)
    {
        return contactInfoRepository.save(contactInfoEntity);

    }
}
