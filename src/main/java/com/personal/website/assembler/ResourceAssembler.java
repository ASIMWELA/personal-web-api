package com.personal.website.assembler;

import com.personal.website.controller.MessageController;
import com.personal.website.controller.ProjectController;
import com.personal.website.controller.UserController;
import com.personal.website.entity.ResourceEntityCollection;
import com.personal.website.model.ResourceModelCollection;
import com.personal.website.model.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ResourceAssembler implements RepresentationModelAssembler<ResourceEntityCollection, ResourceModelCollection>
{
    @Override
    public ResourceModelCollection toModel(ResourceEntityCollection entity)
    {
        return null;
    }

    @Override
    public CollectionModel<ResourceModelCollection> toCollectionModel(Iterable<? extends ResourceEntityCollection> entities)
    {
        CollectionModel<ResourceModelCollection> resources = RepresentationModelAssembler.super.toCollectionModel(entities);
        resources.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
        resources.add(linkTo(MessageController.class).withRel("messaging"));
        return resources;
    }
}
