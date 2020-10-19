package com.personal.website.assembler;

import com.personal.website.controller.ProjectController;
import com.personal.website.controller.UserController;
import com.personal.website.entity.ProjectDetailsEntity;
import com.personal.website.model.Project;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProjectAssembler implements RepresentationModelAssembler<ProjectDetailsEntity, Project>
{
    @Override
    public Project toModel(ProjectDetailsEntity entity)
    {
        Project model = Project.builder().name(entity.getName())
                                    .description(entity.getDescription())
                                    .locationLink(entity.getLocationLink())
                                    .role(entity.getRole())
                                    .collaborators(entity.getCollaborators())
                                    .build()
                                    .add(linkTo(
                                            methodOn(ProjectController.class)
                                                    .getProject(entity.getName()))
                                            .withSelfRel());
        return model;
    }

    @Override
    public CollectionModel<Project> toCollectionModel(Iterable<? extends ProjectDetailsEntity> entities)
    {
        CollectionModel<Project> projectModels = RepresentationModelAssembler.super.toCollectionModel(entities);
        projectModels.add(linkTo(methodOn(ProjectController.class).getAllProjects()).withSelfRel(),linkTo(methodOn(UserController.class).getAllUsers()).withRel("users") );
        return projectModels;
    }
}
