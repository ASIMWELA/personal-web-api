package com.personal.website.service;

import com.personal.website.entity.*;
import com.personal.website.exception.EntityAlreadyExistException;
import com.personal.website.exception.EntityNotFoundException;
import com.personal.website.exception.OperationNotAllowedException;
import com.personal.website.model.ERole;
import com.personal.website.payload.SignUpRequest;
import com.personal.website.repository.*;
import com.personal.website.utils.CheckRole;
import com.personal.website.utils.UidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfilePictureService profilePictureService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ContactInfoService contactInfoService;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private EmploymentRepository employmentRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    public UserEntity saveAdmin(@Valid @RequestBody SignUpRequest signUpRequest)
    {
        if(userRepository.existsByUserName(signUpRequest.getUserName()))
        throw new EntityAlreadyExistException("User Name  already taken. Try different one");


        if(userRepository.existsByEmail(signUpRequest.getEmail()))
            throw new EntityAlreadyExistException("Email already taken. Try different one");

        UserEntity user = new UserEntity(UidGenerator.generateRandomString(15),
                                        signUpRequest.getFirstName(),
                                        signUpRequest.getLastName(),
                                        signUpRequest.getUserName(),
                                        signUpRequest.getEmail(),
                                        signUpRequest.getPassword(),
                                        signUpRequest.getSex());

        user.setPassword(encoder.encode(user.getPassword()));

        RoleEntinty userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(
                ()->new EntityNotFoundException("No role found")
        );
        RoleEntinty adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(
                ()->new EntityNotFoundException("No role found")
        );

        List<RoleEntinty> roles = new ArrayList<>();

        roles.add(userRole);
        roles.add(adminRole);

        user.setRoles(roles);

        return userRepository.save(user);
    }

    public UserEntity saveSubscriber(@Valid @RequestBody SignUpRequest signUpRequest) throws InterruptedException
    {

        if(userRepository.existsByUserName(signUpRequest.getUserName()))
            throw new EntityAlreadyExistException("User Name  already taken. Try different one");


        if(userRepository.existsByEmail(signUpRequest.getEmail()))
            throw new EntityAlreadyExistException("Email already taken. Try different one");

        //allowing subscribers to have only user roles
           RoleEntinty userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(
                   ()->new EntityNotFoundException("No role found")
           );
           UserEntity user = new UserEntity(UidGenerator.generateRandomString(15),signUpRequest.getUserName(),
                                            signUpRequest.getEmail(),
                                            signUpRequest.getPassword());

           user.setRoles(Collections.singletonList(userRole));

           user.setPassword(encoder.encode(user.getPassword()));
           userRepository.save(user);
           sendEmail.sendSuccessEmail(user);

           return user;
    }


    public ProfilePictureEntity updateProfilePicture(@NotBlank MultipartFile file,String userName)
    {
        UserEntity user= userRepository
                                        .findByUserName(userName)
                                        .orElseThrow(()->new EntityNotFoundException("no user with username " + userName));

        if(CheckRole.isAdmin(user.getRoles()))
        {
            ProfilePictureEntity profile = profilePictureService.saveProfilePicture(file);

            profile.setUser(user);
            user.setProfilePicture(profile);
            userRepository.save(user);

            return profile;
        }
        else
        {
            throw new OperationNotAllowedException("Subscribers cannot have profile picture");
        }
    }

    public UserEntity addContactInfo(@NotBlank ContactInfoEntity contactInfoEntity, String userName)
    {
        UserEntity user = userRepository.findByUserName(userName)
                                        .orElseThrow(()->new EntityNotFoundException("No user with username " + userName));

        if(CheckRole.isAdmin(user.getRoles()))
        {
            contactInfoEntity.setUser(user);
            user.setContactInfo(contactInfoEntity);
            contactInfoService.save(contactInfoEntity);

            return userRepository.save(user);
        }
        else
        {
            throw new OperationNotAllowedException("Subscribers cannot have contact information");
        }

    }

    public UserEntity addExperience(@NotBlank ExperienceEntity experienceEntity, String name)
    {
        UserEntity user = userRepository.findByUserName(name)
                .orElseThrow(()->new EntityNotFoundException("No user with username " + name));

        if(experienceRepository.existsByName(experienceEntity.getName()))
            throw new EntityAlreadyExistException("Experience name already exist. Try a different one");

        if(CheckRole.isAdmin(user.getRoles()))
        {
            List<ExperienceEntity> experiences = user.getExperience();
            experienceEntity.setUser(user);
            experiences.add(experienceEntity);
            user.setExperience(experiences);
            experienceRepository.save(experienceEntity);

            return userRepository.save(user);
        }
        else
        {
            throw new OperationNotAllowedException("Subscribers cannot have Experience entity");
        }
    }

    public UserEntity addEmployment(@NotBlank EmploymentEntity employmentEntity, String name)
    {
        UserEntity user = userRepository.findByUserName(name)
                .orElseThrow(()->new EntityNotFoundException("No user with username " + name));

        if(employmentRepository.existsByCompany(employmentEntity.getCompany()))
            throw new EntityAlreadyExistException("Company already added");

        if(CheckRole.isAdmin(user.getRoles()))
        {
            List<EmploymentEntity> employmentEntities = user.getEmployment();
            employmentEntity.setUser(user);
            employmentEntities.add(employmentEntity);
            user.setEmployment(employmentEntities);
            employmentRepository.save(employmentEntity);

            return userRepository.save(user);
        }
        else
        {
            throw new OperationNotAllowedException("Subscribers cannot have employment details");
        }

    }

    public UserEntity addSkill(@NotBlank SkillEntity skillEntity, String name)
    {
        UserEntity user = userRepository.findByUserName(name)
                .orElseThrow(()->new EntityNotFoundException("No user with username " + name));

        if(skillsRepository.existsByTechnology(skillEntity.getTechnology()))
            throw new EntityAlreadyExistException("Technology already added");

        if(CheckRole.isAdmin(user.getRoles()))
        {
            List<SkillEntity> skills = user.getSkills();
            skillEntity.setUser(user);
            skills.add(skillEntity);
            user.setSkills(skills);
            skillsRepository.save(skillEntity);

            return userRepository.save(user);
        }
        else
        {
            throw new OperationNotAllowedException("Subscribers cannot have skills details");
        }

    }

    public UserEntity addEducation(@NotBlank EducationEntity educationEntity, String name)
    {
        UserEntity user = userRepository.findByUserName(name)
                .orElseThrow(()->new EntityNotFoundException("No user with username " + name));

        if(educationRepository.existsByInstitution(educationEntity.getInstitution()))
            throw new EntityAlreadyExistException("Institution  "+educationEntity.getInstitution()+" already added");

        if(CheckRole.isAdmin(user.getRoles()))
        {
            List<EducationEntity> educationEntityList = user.getEducation();
            educationEntity.setUser(user);
            educationEntityList.add(educationEntity);
            user.setEducation(educationEntityList);
            educationRepository.save(educationEntity);

            return userRepository.save(user);  }
        else
        {
            throw new OperationNotAllowedException("Subscribers cannot education background");
        }

    }
}
