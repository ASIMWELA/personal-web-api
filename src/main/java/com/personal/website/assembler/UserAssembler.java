package com.personal.website.assembler;

import com.personal.website.controller.ProjectController;
import com.personal.website.controller.UserController;
import com.personal.website.entity.UserEntity;
import com.personal.website.model.User;
import com.personal.website.utils.CheckRole;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<UserEntity, User>
{
    @Override
    public User toModel(UserEntity entity)
    {
        User model = null;

       if(CheckRole.isAdmin(entity.getRoles())){
           model =  User.builder()
                   .email(entity.getEmail())
                   .firstName(entity.getFirstName())
                   .age(entity.getAge())
                   .sex(entity.getSex())
                   .dateOfBirth(entity.getDateOfBirth())
                   .lastName(entity.getLastName())
                   .userName(entity.getUserName())
                   .isOnline(entity.isOnline())
                   .uid(entity.getUid())
                   .profilePicPath(entity.getProfilePicPath())
                   .build()
                   .add(linkTo(
                           methodOn(UserController.class)
                                   .getUser(entity.getUid()))
                           .withSelfRel())
                   .add(linkTo(
                           methodOn(ProjectController.class)
                            .getAllProjects()).withRel("projects"));
       }
       else{
           model = User.builder()
                   .email(entity.getEmail())
                   .userName(entity.getUserName())
                   .isOnline(entity.isOnline())
                   .uid(entity.getUid())
                   .build().add(linkTo(
                           methodOn(UserController.class)
                                   .getUser(entity.getUid()))
                           .withSelfRel());
       }

       return model;
    }

    @Override
    public CollectionModel<User> toCollectionModel(Iterable<? extends UserEntity> entities) {
        CollectionModel<User> users = RepresentationModelAssembler.super.toCollectionModel(entities);
        users.add(linkTo(methodOn(UserController.class).getResources()).withRel("resources"));
        users.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
        return users;
    }
}
