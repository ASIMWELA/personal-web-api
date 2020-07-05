package com.personal.website.service;

import com.personal.website.entity.SubscriberEntity;
import com.personal.website.exception.EntityAlreadyExistException;
import com.personal.website.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriberService
{
    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SendEmail sendEmail;

    public SubscriberEntity subscribe(SubscriberEntity subscriberEntity) throws InterruptedException {
        if(subscriberRepository.existsByEmail(subscriberEntity.getEmail()))
        {
            throw new EntityAlreadyExistException("Email already in use. Try a different one");
        }

            SubscriberEntity userSubscribed=subscriberRepository.save(
                                      subscriberEntity.builder()
                                                .firstName(subscriberEntity.getFirstName())
                                                .lastName(subscriberEntity.getLastName())
                                                .email(subscriberEntity.getEmail())
                                                .password(subscriberEntity.getPassword())
                                                .build());

        sendEmail.sendSuccessEmail(userSubscribed);

        return userSubscribed;


    }
}
