package com.personal.website.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.website.entity.ProjectDetailsEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class Project extends RepresentationModel<Project>
{

    //projects
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String role;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] collaborators;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String locationLink;

    public static Project build(ProjectDetailsEntity entity)
    {

        Project project = null;

                if(entity.getCollaborators()==null)
                {
                    project = Project.builder()
                            .locationLink(entity.getLocationLink())
                            .role(entity.getRole())
                            .description(entity.getDescription())
                            .name(entity.getName())
                            .build();
                }
                else{
                  project=  Project.builder()
                            .locationLink(entity.getLocationLink())
                            .collaborators(entity.getCollaborators())
                            .role(entity.getRole())
                            .description(entity.getDescription())
                            .name(entity.getName())
                            .build();

                }
        return project;

    }

}
