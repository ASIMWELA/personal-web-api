package com.personal.website.controller;

import com.personal.website.assembler.UserAssembler;
import com.personal.website.entity.*;
import com.personal.website.exception.EntityNotFoundException;
import com.personal.website.model.User;
import com.personal.website.payload.ApiResponse;
import com.personal.website.repository.ExperienceRepository;
import com.personal.website.repository.SkillsRepository;
import com.personal.website.repository.UserRepository;
import com.personal.website.service.ProfilePictureService;
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
@RequestMapping("/api/v1")
public class UserController
{
    @Autowired
    private ProfilePictureService profilePictureService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private SkillsRepository skillsRepository;

    @RequestMapping(
            value="/users",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<User>> getAllUsers()
    {
        List<UserEntity> users = userRepository.findAll();

        return new ResponseEntity<>(userAssembler.toCollectionModel(users), HttpStatus.OK);

    }

    @RequestMapping(
            value = "/users/{uid}",
            method = RequestMethod.GET,
            produces = {
                            MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE}
                    )
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable("uid") String iud)
    {
       User user = User.build(userRepository.findByUid(iud).orElseThrow(() -> new EntityNotFoundException("NO User with id " + iud)))
                            .add(linkTo(methodOn(UserController.class)
                                    .getAllUsers()).withRel("users"));

       return user;
    }

    @RequestMapping(
            value="/profile/{userName}",
            method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    public ProfilePictureEntity upload(@RequestParam("profile")MultipartFile file, @PathVariable("userName") String userName)
    {
       return userService.updateProfilePicture(file, userName);
    }

    @RequestMapping(
            value="/contact-info/{userName}",
            method = RequestMethod.PUT,
            produces = {
                            MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE
                        }
                     )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addContactInfo(@RequestBody ContactInfoEntity contactInfoEntity, @PathVariable("userName") String userName)
    {
        userService.addContactInfo(contactInfoEntity, userName);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "contact details successfully added" ),HttpStatus.OK);

    }
    @RequestMapping(
            value="/experience/{userName}",
            method = RequestMethod.PUT,
            produces = {
                            MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE
                        }
                    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addExperience(@RequestBody ExperienceEntity experienceEntity, @PathVariable("userName") String userName)
    {
        userService.addExperience(experienceEntity, userName);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Experience details successfully added" ),HttpStatus.OK);

    }

    @RequestMapping(value="/employment/{userName}",
            method = RequestMethod.PUT,
            produces = {
                            MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE
                        }
                     )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addEmployment(@RequestBody EmploymentEntity employmentEntity, @PathVariable("userName") String userName)
    {
        userService.addEmployment(employmentEntity, userName);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Employment details successfully added" ),HttpStatus.OK);
    }

    @RequestMapping(
            value="/skill/{userName}",
            method = RequestMethod.PUT,
            produces = {
                            MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE
                        }
                      )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addSkills(@RequestBody SkillEntity skillEntity, @PathVariable("userName") String userName)
    {
        userService.addSkill(skillEntity, userName);

         return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Skills details successfully added" ),HttpStatus.OK);
    }

    @RequestMapping(
            value="/education/{userName}",
            method = RequestMethod.PUT,
            produces = {
                            MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE}
                    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addEducation(@RequestBody EducationEntity educationEntity, @PathVariable("userName") String userName)
    {
        userService.addEducation(educationEntity, userName);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Education details successfully added" ),HttpStatus.OK);

    }

}
