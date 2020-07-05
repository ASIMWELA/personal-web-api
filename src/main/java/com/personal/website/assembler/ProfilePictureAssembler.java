package com.personal.website.assembler;

import com.personal.website.entity.ProfilePictureEntity;
import com.personal.website.model.ProfilePictureModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public class ProfilePictureAssembler implements RepresentationModelAssembler<ProfilePictureEntity, ProfilePictureModel>
{
    @Override
    public ProfilePictureModel toModel(ProfilePictureEntity entity)
    {
        return ProfilePictureModel.builder()
                                   .fileName(entity.getFileName())
                                    .fileType(entity.getFileType()).
                                    data(entity.getData()).build();

    }

    @Override
    public CollectionModel<ProfilePictureModel> toCollectionModel(Iterable<? extends ProfilePictureEntity> entities)
    {
        return null;
    }
}
