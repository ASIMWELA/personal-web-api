package com.personal.website.controller;

import com.personal.website.entity.ContactInfoEntity;
import com.personal.website.entity.ProjectDetailsEntity;
import com.personal.website.entity.UserEntity;
import com.personal.website.payload.SignUpRequest;
import com.personal.website.service.ProfilePictureService;
import com.personal.website.service.ProjectDetailsService;
import com.personal.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private ProjectDetailsService projectDetailsService;

//    @RequestMapping(value = "/upload-profile", method = RequestMethod.POST)
//    public ResponseEntity<ProfilePictureEntity> uploadProfile(@RequestParam("profile")MultipartFile file)
//    {
//
//        return  new ResponseEntity<>(profilePictureService.saveProfilePicture(file), HttpStatus.OK);
//
//    }

    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserEntity saveUser(@RequestBody SignUpRequest signUpRequest)
    {
        return userService.saveAdmin(signUpRequest);
    }

    @PreAuthorize("hasRole(ADMIN)")
    @RequestMapping(value="/upload-profile/{userName}", method = RequestMethod.PUT)
    public UserEntity upload(@RequestParam("profile")MultipartFile file, @PathVariable("userName") String userName)
    {
       return userService.updateProfilePicture(file, userName);
    }

    @RequestMapping(value="/contact-info/{userName}",
            method = RequestMethod.PUT,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserEntity addContactInfo(@RequestBody ContactInfoEntity contactInfoEntity, @PathVariable("userName") String userName)
    {
        return userService.addContactInfo(contactInfoEntity, userName);
    }


    @RequestMapping(value = "/add-project", method = RequestMethod.POST)
    public ProjectDetailsEntity subscribe(@RequestBody ProjectDetailsEntity projectDetailsEntity) throws InterruptedException
    {

        return projectDetailsService.saveProject(projectDetailsEntity);
    }

}
