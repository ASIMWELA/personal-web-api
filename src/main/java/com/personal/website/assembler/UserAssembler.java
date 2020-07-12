package com.personal.website.assembler;

import com.personal.website.controller.ProjectController;
import com.personal.website.controller.UserController;
import com.personal.website.entity.UserEntity;
import com.personal.website.model.UserModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<UserEntity, UserModel>
{
    @Override
    public UserModel toModel(UserEntity entity)
    {

       UserModel model =  UserModel.builder()
                                        .email(entity.getEmail())
                                        .userName(entity.getUserName())
                                        .uid(entity.getUid())
                                        .build()
                                        .add(linkTo(
                                                    methodOn(UserController.class)
                                                            .getUser(entity.getUid()))
                                                            .withSelfRel());
       if(entity.getRoles().size()>1){
           model.add(linkTo(methodOn(ProjectController.class)
                   .getAllProjects()).withRel("projects"));
       }

       return model;
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends UserEntity> entities) {
        CollectionModel<UserModel> userModels = RepresentationModelAssembler.super.toCollectionModel(entities);
        userModels.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
        return userModels;
    }
}
