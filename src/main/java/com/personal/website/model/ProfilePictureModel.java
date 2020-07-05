package com.personal.website.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.website.entity.ProfilePictureEntity;
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
public class ProfilePictureModel extends RepresentationModel<ProfilePictureModel>
{
    public ProfilePictureModel(ProfilePictureEntity profilePictureEntity)
    {

    }
    private Long id;

    private String fileName;

    private String fileType;

    private byte[] data;

}
