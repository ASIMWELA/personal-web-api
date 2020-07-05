package com.personal.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.AssertFalse;
import java.util.List;

@Entity
@Table(name="project_details")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProjectDetailsEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name="id")
    private Long id;

    @Column(name="name", unique = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @Column(name="description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @Column(name="role")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String role;

    @Column(name="collaborators")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] collaborators;

    @Column(name="location_link")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String locationLink;


}
