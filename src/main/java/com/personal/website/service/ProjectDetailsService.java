package com.personal.website.service;

import com.personal.website.entity.ProjectDetailsEntity;
import com.personal.website.event.ProjectSavedEvent;
import com.personal.website.exception.EntityAlreadyExistException;
import com.personal.website.repository.ProjectDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ProjectDetailsService
{
    @Autowired
    private ProjectDetailsRepository projectDetailsRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public ProjectDetailsEntity saveProject(ProjectDetailsEntity projectDetailsEntity)
    {
        if(projectDetailsRepository.existsByName(projectDetailsEntity.getName()))
            throw new EntityAlreadyExistException("Project already added");

        ProjectDetailsEntity project = projectDetailsEntity.builder()
                                                            .name(projectDetailsEntity.getName())
                                                            .description(projectDetailsEntity.getDescription())
                                                            .role(projectDetailsEntity.getRole())
                                                            .locationLink(projectDetailsEntity.getLocationLink())
                                                            .collaborators(projectDetailsEntity.getCollaborators())
                                                            .build();

        projectDetailsRepository.save(project);
        ProjectSavedEvent event = new ProjectSavedEvent(project);
        applicationEventPublisher.publishEvent(event);

        return project;
    }
}
