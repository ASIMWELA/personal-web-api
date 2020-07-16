package com.personal.website.controller;

import com.personal.website.assembler.ProjectAssembler;
import com.personal.website.entity.ProjectDetailsEntity;
import com.personal.website.exception.EntityNotFoundException;
import com.personal.website.model.Project;
import com.personal.website.repository.ProjectDetailsRepository;
import com.personal.website.service.ProjectDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    @Autowired
    private ProjectDetailsRepository projectDetailsRepository;

    @Autowired
    private ProjectAssembler projectAssembler;

    @Autowired
    private ProjectDetailsService projectDetailsService;


    @RequestMapping(
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<Project>> getAllProjects() {
        List<ProjectDetailsEntity> projects = projectDetailsRepository.findAll();

        return new ResponseEntity<>(projectAssembler.toCollectionModel(projects), HttpStatus.OK);

    }

    @RequestMapping(
            value = "/{projectName}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Project getProject(@PathVariable("projectName") String projectName) {

           ProjectDetailsEntity entity = projectDetailsRepository.findByName(projectName).orElseThrow(() -> new EntityNotFoundException("NO User with id " + projectName));

           Project model = Project.build(entity);
           model.add(linkTo(methodOn(ProjectController.class)
                        .getAllProjects()).withRel("projects"));

        return model;
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectDetailsEntity subscribe(@RequestBody ProjectDetailsEntity projectDetailsEntity) throws InterruptedException
    {

        return projectDetailsService.saveProject(projectDetailsEntity);
    }

}
