package com.personal.website.controller;

import com.google.common.collect.Lists;
import com.personal.website.assembler.UserAssembler;
import com.personal.website.entity.*;
import com.personal.website.exception.EntityNotFoundException;
import com.personal.website.model.User;
import com.personal.website.payload.ApiResponse;
import com.personal.website.payload.UpdateAdminRequest;
import com.personal.website.repository.*;
import com.personal.website.service.ChatMessageService;
import com.personal.website.service.ProfilePictureService;
import com.personal.website.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
    PasswordEncoder encoder;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private SkillsRepository skillsRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private EmploymentRepository employmentRepository;

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
            value="/users/{userName}",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateAdmin(@NotNull @RequestBody UpdateAdminRequest request, @PathVariable("userName") String userName)
    {
        UserEntity entity = userRepository.findByUserName(userName).orElseThrow(
                ()->new EntityNotFoundException("No user with username" + userName)
        );



        String password = encoder.encode(request.getPassword());

        userRepository.updateUser(request.getUserName(), request.getFirstName(), request.getLastName(), request.getEmail(),password, entity.getUserName());

       return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Admin details updated successfully" ),HttpStatus.OK);
//
    }

    @RequestMapping(
            value="/users/{userName}",
            method = RequestMethod.DELETE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteAdmin(@PathVariable("userName") String userName)
    {
        UserEntity entity = userRepository.findByUserName(userName).orElseThrow(
                ()->new EntityNotFoundException("No user with username" + userName)
        );

       // entity.getProfilePicture().setUser(null);
        ProfilePictureEntity pPic = entity.getProfilePicture();
        if(pPic!=null){
            entity.getProfilePicture().setUser(null);
        }

        ContactInfoEntity cInfo = entity.getContactInfo();
        if(cInfo!=null){
            entity.getContactInfo().setUser(null);
        }
//        entity.getExperience().forEach(em->{
//            em.setUser(null);
//        });

        experienceRepository.deleteExperience(entity);
        skillsRepository.deleteSkill(entity);
        educationRepository.deleteEducationDetails(entity);
        employmentRepository.deleteEmpDetails(entity);
        //de-link a user with its associated entities
        entity.setContactInfo(null);
        entity.setSkills(null);
        entity.setEducation(null);
        entity.setEmployment(null);
        entity.setProfilePicture(null);
        entity.setExperience(null);
        entity.setRoles(null);

        userRepository.flush();
        userRepository.delete(entity);
        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Admin details deleted successfully" ),HttpStatus.OK);
//
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
    public ResponseEntity<ApiResponse> addContactInfo(@NotNull @RequestBody  ContactInfoEntity contactInfoEntity, @PathVariable("userName") String userName)
    {
        userService.addContactInfo(contactInfoEntity, userName);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "contact details successfully added" ),HttpStatus.OK);

    }

    @RequestMapping(
            value="/contact-info/update/{phoneNumber}",
            method = RequestMethod.PUT,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateContactInfo(@NotNull @RequestBody  ContactInfoEntity contactInfoEntity, @PathVariable("phoneNumber") String phoneNumber)
    {
        ContactInfoEntity contactInfo = contactInfoRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                ()->new EntityNotFoundException("No contact details found")
        );
        contactInfoRepository.updateContactInfo(
                contactInfoEntity.getCity(),
                contactInfoEntity.getPhysicalAddress(),
                contactInfoEntity.getPhoneNumber(),
                contactInfoEntity.getCountry(),
                phoneNumber
        );


        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "contact details successfully updated" ),HttpStatus.OK);

    }

    @RequestMapping(
            value="/contact-info/{phoneNumber}/{userName}",
            method = RequestMethod.DELETE,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteContactDetails( @PathVariable("phoneNumber") String phoneNumber, @PathVariable("userName") String userName)
    {
        ContactInfoEntity contactInfo = contactInfoRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                ()->new EntityNotFoundException("No contact details found")
        );
        UserEntity userEntity = userRepository.findByUserName(userName).orElseThrow(
                ()->new EntityNotFoundException("No user found with name " +userName)
        );

        userEntity.setContactInfo(null);
        contactInfoRepository.delete(contactInfo);

        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "contact details successfully deleted" ),HttpStatus.OK);

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
    public ResponseEntity<ApiResponse> addExperience(@NotNull @RequestBody ExperienceEntity experienceEntity, @PathVariable("userName") String userName)
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
    public ResponseEntity<ApiResponse> addEmployment(@NotNull @RequestBody EmploymentEntity employmentEntity, @PathVariable("userName") String userName)
    {
        userService.addEmployment(employmentEntity, userName);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Employment details successfully added" ),HttpStatus.OK);
    }

    @RequestMapping(value="/employment/{userName}/{compNAme}",
            method = RequestMethod.DELETE,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteEmpDetails(@PathVariable("compNAme")String companyName, @PathVariable("userName") String userName)
    {
        UserEntity user = userRepository.findByUserName(userName).orElseThrow(
                ()->new EntityNotFoundException("No user with user name " + userName)
        );

        EmploymentEntity employmentEntity = employmentRepository.findByCompany(companyName).orElseThrow(
                ()->new EntityNotFoundException("No employment details for company " + companyName)
        );

        employmentRepository.deleteEmpDetails(employmentEntity.getCompany(), user);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Employment details successfully Deleted" ),HttpStatus.OK);
    }

    @RequestMapping(value="/employment/update/{compNAme}",
            method = RequestMethod.PUT,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateEmpDetails(@NotNull @RequestBody EmploymentEntity entity, @PathVariable("compNAme")String companyName)
    {

        EmploymentEntity emp =  employmentRepository.findByCompany(companyName).orElseThrow(
                ()->new EntityNotFoundException("No employment details for company " + companyName)
        );

        employmentRepository.updateEmpInfo(entity.getCompany(), entity.getAccomplishments(),entity.getAvailability(), entity.getDuration(), emp.getCompany());
        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Employment details successfully Updated" ),HttpStatus.OK);
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
    public ResponseEntity<ApiResponse> addSkills(@NotNull @RequestBody SkillEntity skillEntity, @PathVariable("userName") String userName)
    {
        userService.addSkill(skillEntity, userName);

         return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Skills details successfully added" ),HttpStatus.OK);
    }

    @RequestMapping(
            value="/skill/update/{userName}",
            method = RequestMethod.PUT,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateSkill(@NotNull @RequestBody SkillEntity skillEntity, @PathVariable("userName") String userName)
    {
        userService.updateSkill(skillEntity, userName);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Skills details successfully updated" ),HttpStatus.OK);
    }


    @RequestMapping(
            value="/education/{userName}",
            method = RequestMethod.PUT,
            produces = {
                            MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE}
                    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addEducation(@NotNull @RequestBody EducationEntity educationEntity, @PathVariable("userName") String userName)
    {
        userService.addEducation(educationEntity, userName);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Education details successfully added" ),HttpStatus.OK);

    }

    @RequestMapping(
            value="/education/update/{institution}",
            method = RequestMethod.PUT,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE}
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateEducation(@NotNull @RequestBody EducationEntity educationEntity, @PathVariable("institution") String institution)
    {
        EducationEntity educationEntity1 = educationRepository.findByInstitution(institution).orElseThrow(
                ()->new EntityNotFoundException("No details on specified institution " + institution)
        );

        educationEntity1.setAwards(educationEntity.getAwards());
        educationEntity1.setInstitution(educationEntity.getInstitution());
        educationEntity1.setPeriod(educationEntity.getPeriod());
        educationRepository.save(educationEntity1);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Education details successfully updated" ),HttpStatus.OK);

    }
    @RequestMapping(
            value="/education/{userName}/{institution}",
            method = RequestMethod.DELETE,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE}
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteEducation(@PathVariable("userName") String userName, @PathVariable("institution") String institution)
    {
        EducationEntity educationEntity1 = educationRepository.findByInstitution(institution).orElseThrow(
                ()->new EntityNotFoundException("No details on specified institution " + institution)
        );

        UserEntity user = userRepository.findByUserName(userName).orElseThrow(
        ()->new EntityNotFoundException("No user with userName " + userName)
        );

        educationRepository.deleteEducationDetails(user, educationEntity1.getInstitution());

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "Education details successfully deleted" ),HttpStatus.OK);

    }


    @RequestMapping(
            value = "/skill/{tech}/{user}",
            method=RequestMethod.DELETE
    )
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<ApiResponse> deleteSkill(@PathVariable("tech") String tech, @PathVariable("user") String userName)
    {
        UserEntity user = userRepository.findByUserName(userName).orElseThrow(
                ()->new EntityNotFoundException("No user found with user name" + userName)
        );


       SkillEntity skill = skillsRepository.findByTechnology(tech).orElseThrow(
                ()->new EntityNotFoundException(("Skill not found with name" + tech))
        );
        skillsRepository.deleteSkill(skill.getTechnology(), user);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK, HttpStatus.OK.value(), "Skill deleted successfully"), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/experience/{name}/{user}",
            method=RequestMethod.DELETE
    )
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<ApiResponse> deleteExperience(@PathVariable("name") String name, @PathVariable("user") String userName)
    {

        userService.deleteUserExperience(name, userName);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK, HttpStatus.OK.value(), "Experience deleted successfully"), HttpStatus.OK);
    }


    @RequestMapping(value="/chat/messages",  method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE}
    )
    public List<MessageEntity> getMessages()
    {
        Pageable page = PageRequest.of(0,40);
        List<MessageEntity> messages = messageRepository.getAllMessages(page);
        List<MessageEntity> recentMessages = Lists.reverse(messages);


        return recentMessages;
    }

    @RequestMapping(value="/chat/totalUsersOnline",  method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<JSONObject> getTotalOnlineUsers()
    {
        int total = userRepository.getTotalOnlineUsers();
        JSONObject j = new JSONObject();
        j.appendField("totalOnlineUsers", total);

        return new ResponseEntity<JSONObject>(j, HttpStatus.OK);
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable String senderId,
                                                @PathVariable String recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable Long id) {
        return ResponseEntity
                .ok(chatMessageService.findById(id));
    }

}
