package com.personal.website.controller;

import com.personal.website.assembler.UserAssembler;
import com.personal.website.entity.ContactInfoEntity;
import com.personal.website.entity.ProjectDetailsEntity;
import com.personal.website.entity.UserEntity;
import com.personal.website.exception.EntityNotFoundException;
import com.personal.website.model.UserModel;
import com.personal.website.payload.SignUpRequest;
import com.personal.website.repository.UserRepository;
import com.personal.website.service.ProfilePictureService;
import com.personal.website.service.ProjectDetailsService;
import com.personal.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private ProfilePictureService profilePictureService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAssembler userAssembler;


    @Autowired
    private ProjectDetailsService projectDetailsService;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<UserModel>> getAllUsers()
    {
        List<UserEntity> users = userRepository.findAll();

        return new ResponseEntity<>(userAssembler.toCollectionModel(users), HttpStatus.OK);

    }

    @RequestMapping(
            value = "/{uid}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserModel getUser(@PathVariable("uid") String iud)
    {
       UserModel userModel= UserModel.build(userRepository.findByUid(iud).orElseThrow(() -> new EntityNotFoundException("NO User with id " + iud)))
                            .add(linkTo(methodOn(UserController.class)
                                    .getAllUsers()).withRel("users"));

       return userModel;
    }

    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserEntity saveUser(@RequestBody SignUpRequest signUpRequest)
    {
        return userService.saveAdmin(signUpRequest);
    }

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
