package com.personal.website.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.website.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

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
    private Long id;

    private String uid;

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    private String password;

}
