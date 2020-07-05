package com.personal.website.service;

import com.personal.website.entity.ContactInfoEntity;
import com.personal.website.entity.ProfilePictureEntity;
import com.personal.website.entity.UserEntity;
import com.personal.website.exception.EntityAlreadyExistException;
import com.personal.website.exception.EntityNotFoundException;
import com.personal.website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfilePictureService profilePictureService;

    @Autowired
    private ContactInfoService contactInfoService;

    public UserEntity saveUser(UserEntity user)
    {
        if(userRepository.existsByUserNameOrEmail(user.getUserName(), user.getEmail()))
            throw new EntityAlreadyExistException("User Name or Email already taken. Try different one");

        return userRepository.save(user);
    }

    public UserEntity updateProfilePicture(MultipartFile file,String userName)
    {
        UserEntity user= userRepository
                                        .findByUserName(userName)
                                        .orElseThrow(()->new EntityNotFoundException("no user with username " + userName));
        ProfilePictureEntity profile = profilePictureService.saveProfilePicture(file);

        profile.setUser(user);
        user.setProfilePicture(profile);

        return userRepository.save(user);
    }

    public UserEntity addContactInfo(ContactInfoEntity contactInfoEntity, String userName)
    {
        UserEntity user = userRepository.findByUserName(userName)
                                        .orElseThrow(()->new EntityNotFoundException("No user with username " + userName));

        contactInfoEntity.setUser(user);
        user.setContactInfo(contactInfoEntity);
        contactInfoService.save(contactInfoEntity);

        return userRepository.save(user);
    }
}
