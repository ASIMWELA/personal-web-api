package com.personal.website.controller;

import com.personal.website.entity.ContactInfoEntity;
import com.personal.website.entity.ProjectDetailsEntity;
import com.personal.website.entity.SubscriberEntity;
import com.personal.website.entity.UserEntity;
import com.personal.website.service.ProfilePictureService;
import com.personal.website.service.ProjectDetailsService;
import com.personal.website.service.SubscriberService;
import com.personal.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private ProfilePictureService profilePictureService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private ProjectDetailsService projectDetailsService;

//    @RequestMapping(value = "/upload-profile", method = RequestMethod.POST)
//    public ResponseEntity<ProfilePictureEntity> uploadProfile(@RequestParam("profile")MultipartFile file)
//    {
//
//        return  new ResponseEntity<>(profilePictureService.saveProfilePicture(file), HttpStatus.OK);
//
//    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public UserEntity saveUser(@RequestBody UserEntity entity)
    {
        return userService.saveUser(entity);
    }

    @RequestMapping(value="/upload-profile/{userName}", method = RequestMethod.PUT)
    public UserEntity upload(@RequestParam("profile")MultipartFile file, @PathVariable("userName") String userName)
    {
       return userService.updateProfilePicture(file, userName);
    }

    @RequestMapping(value="/contact-info/{userName}", method = RequestMethod.PUT)
    public UserEntity addContactInfo(@RequestBody ContactInfoEntity contactInfoEntity, @PathVariable("userName") String userName)
    {
        return userService.addContactInfo(contactInfoEntity, userName);
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public SubscriberEntity subscribe(@RequestBody SubscriberEntity subscriberEntity) throws InterruptedException
    {

        return subscriberService.subscribe(subscriberEntity);
    }

    @RequestMapping(value = "/add-project", method = RequestMethod.POST)
    public ProjectDetailsEntity subscribe(@RequestBody ProjectDetailsEntity projectDetailsEntity) throws InterruptedException
    {

        return projectDetailsService.saveProject(projectDetailsEntity);
    }

}
