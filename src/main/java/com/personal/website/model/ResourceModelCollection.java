package com.personal.website.model;

import com.personal.website.entity.ProjectDetailsEntity;
import com.personal.website.entity.UserEntity;
import lombok.Builder;
import org.springframework.hateoas.RepresentationModel;

@Builder
public class ResourceModelCollection extends RepresentationModel<ResourceModelCollection>
{
    private UserEntity users;
    private ProjectDetailsEntity projects;

    public static ResourceModelCollection build(UserEntity users, ProjectDetailsEntity projects){

        return ResourceModelCollection.builder().users(users).projects(projects).build();
    }
}
