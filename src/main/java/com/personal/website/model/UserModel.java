package com.personal.website.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.website.controller.ProjectController;
import com.personal.website.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
public class UserModel extends RepresentationModel<UserModel>
{
    public UserModel(UserEntity profilePictureEntity)
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
    private String lastName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sex;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ContactInfoEntity contactInfo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ExperienceEntity> experienceEntity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RoleEntinty> roles;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProfilePictureEntity profilePicture;

    @JsonIgnore
    private String password;

    public static UserModel build(UserEntity entity)
    {
        UserModel model =UserModel.builder()
                                .firstName(entity.getFirstName())
                                .lastName(entity.getLastName())
                                .email(entity.getEmail())
                                .userName(entity.getUserName())
                                .sex(entity.getSex())
                                .roles(entity.getRoles())
                                .uid(entity.getUid())
                                .contactInfo(entity.getContactInfo())
                                .experienceEntity(entity.getExperience())
                                .profilePicture(entity.getProfilePicture())
                                .build();

        if(entity.getRoles().size()>1){
            model.add(linkTo(methodOn(ProjectController.class)
                    .getAllProjects()).withRel("projects"));
        }

        return model;
    }

}
