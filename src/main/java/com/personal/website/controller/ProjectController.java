package com.personal.website.controller;

import com.personal.website.assembler.ProjectAssembler;
import com.personal.website.entity.ProjectDetailsEntity;
import com.personal.website.exception.EntityNotFoundException;
import com.personal.website.model.Project;
import com.personal.website.payload.ApiResponse;
import com.personal.website.repository.ProjectDetailsRepository;
import com.personal.website.service.ProjectDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE}
                    )
    public Project getProject(@PathVariable("projectName") String projectName) {

           ProjectDetailsEntity entity = projectDetailsRepository.findByName(projectName).orElseThrow(() -> new EntityNotFoundException("NO User with id " + projectName));

           Project model = Project.build(entity);
           model.add(linkTo(methodOn(ProjectController.class)
                        .getAllProjects()).withRel("projects"));

        return model;
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE}
    )
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ApiResponse> addProject(@NotNull @RequestBody ProjectDetailsEntity projectDetailsEntity) throws InterruptedException
    {

        projectDetailsService.saveProject(projectDetailsEntity);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.CREATED,HttpStatus.CREATED.value(), "project added successfully" ),HttpStatus.CREATED);

    }
    @RequestMapping(value="/{projectName}",
            method = RequestMethod.PUT,
            produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE}
    )
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ApiResponse> editProjectEntity(@PathVariable("projectName") String projectName, @NotBlank @RequestBody ProjectDetailsEntity projectDetailsEntity) throws InterruptedException
    {

        projectDetailsService.editProject(projectName,projectDetailsEntity);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "project updated successfully" ),HttpStatus.OK);

    }
    @RequestMapping(value="/{projectName}",
            method = RequestMethod.DELETE,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE}
    )
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ApiResponse> deleteProject(@PathVariable("projectName") String projectName) throws InterruptedException
    {
        ProjectDetailsEntity entity = projectDetailsRepository.findByName((projectName)).orElseThrow(()->new EntityNotFoundException("No project found with name "+ projectName));

        projectDetailsRepository.delete(entity);

        return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK,HttpStatus.OK.value(), "project deletion successful" ),HttpStatus.OK);

    }
}
