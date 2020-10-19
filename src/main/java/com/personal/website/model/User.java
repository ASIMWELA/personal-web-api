package com.personal.website.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.website.controller.ProjectController;
import com.personal.website.entity.*;
import com.personal.website.utils.CheckRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
public class User extends RepresentationModel<User>
{
    public User(UserEntity profilePictureEntity)
    {

    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String uid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int age;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean isOnline;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sex;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate dateOfBirth;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ContactInfoEntity contactInfo;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ExperienceEntity> experience;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<EmploymentEntity> employment;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SkillEntity> skills;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<EducationEntity> education;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RoleEntinty> roles;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProfilePictureEntity profilePicture;

    public static User build(UserEntity entity)
    {   User model = null;

        if(CheckRole.isAdmin(entity.getRoles()))
        {
           model =  User.builder()
                    .firstName(entity.getFirstName())
                    .lastName(entity.getLastName())
                    .email(entity.getEmail())
                    .userName(entity.getUserName())
                    .sex(entity.getSex())
                    .roles(entity.getRoles())
                    .uid(entity.getUid())
                    .age(entity.getAge())
                    .dateOfBirth(entity.getDateOfBirth())
                    .contactInfo(entity.getContactInfo())
                    .experience(entity.getExperience())
                    .employment(entity.getEmployment())
                    .skills(entity.getSkills())
                    .education(entity.getEducation())
                    .isOnline(entity.isOnline())
                    .profilePicture(entity.getProfilePicture())
                    .build().add(linkTo(methodOn(ProjectController.class)
                    .getAllProjects()).withRel("projects"));
        }
        else
        {
            model = User.builder().userName(entity.getUserName())
                        .email(entity.getEmail())
                        .isOnline(entity.isOnline())
                        .roles(entity.getRoles())
                        .uid(entity.getUid())
                        .build();
        }
        return model;
    }

}
