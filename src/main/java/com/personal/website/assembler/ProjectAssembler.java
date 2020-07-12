package com.personal.website.assembler;

import com.personal.website.controller.ProjectController;
import com.personal.website.controller.UserController;
import com.personal.website.entity.ProjectDetailsEntity;
import com.personal.website.model.ProjectModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProjectAssembler implements RepresentationModelAssembler<ProjectDetailsEntity, ProjectModel>
{
    @Override
    public ProjectModel toModel(ProjectDetailsEntity entity)
    {
        ProjectModel model = ProjectModel.builder().name(entity.getName())
                                    .description(entity.getDescription())
                                    .build()
                                    .add(linkTo(
                                            methodOn(ProjectController.class)
                                                    .getProject(entity.getName()))
                                            .withSelfRel());
        return model;
    }

    @Override
    public CollectionModel<ProjectModel> toCollectionModel(Iterable<? extends ProjectDetailsEntity> entities)
    {
        CollectionModel<ProjectModel> projectModels = RepresentationModelAssembler.super.toCollectionModel(entities);
        projectModels.add(linkTo(methodOn(ProjectController.class).getAllProjects()).withSelfRel(),linkTo(methodOn(UserController.class).getAllUsers()).withRel("users") );
        return projectModels;
    }
}
