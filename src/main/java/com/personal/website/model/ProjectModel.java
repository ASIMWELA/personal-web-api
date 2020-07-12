package com.personal.website.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.website.entity.ProjectDetailsEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;

@Data
@Builder
public class ProjectModel extends RepresentationModel<ProjectModel>
{

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

    public static ProjectModel build(ProjectDetailsEntity entity)
    {
        return
                ProjectModel.builder()
                            .locationLink(entity.getLocationLink())
                            .collaborators(entity.getCollaborators())
                            .role(entity.getRole())
                            .description(entity.getDescription())
                            .name(entity.getName())
                            .build();
    }

}
