package com.personal.website.controller;

import com.personal.website.assembler.ProjectAssembler;
import com.personal.website.entity.ProjectDetailsEntity;
import com.personal.website.entity.UserEntity;
import com.personal.website.exception.EntityNotFoundException;
import com.personal.website.model.ProjectModel;
import com.personal.website.model.UserModel;
import com.personal.website.repository.ProjectDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectDetailsRepository projectDetailsRepository;

    @Autowired
    private ProjectAssembler projectAssembler;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<ProjectModel>> getAllProjects() {
        List<ProjectDetailsEntity> projects = projectDetailsRepository.findAll();

        return new ResponseEntity<>(projectAssembler.toCollectionModel(projects), HttpStatus.OK);

    }

    @RequestMapping(
            value = "/{projectName}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ProjectModel getProject(@PathVariable("projectName") String projectName) {

           ProjectDetailsEntity entity = projectDetailsRepository.findByName(projectName).orElseThrow(() -> new EntityNotFoundException("NO User with id " + projectName));

           ProjectModel model = ProjectModel.build(entity);
           model.add(linkTo(methodOn(ProjectController.class)
                        .getAllProjects()).withRel("projects"));

        return model;
    }
}
