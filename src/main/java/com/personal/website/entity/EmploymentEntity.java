package com.personal.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="employment_entity")
@Table(name="employment_entity")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmploymentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(name="company", nullable = false, unique = true)
    private String company;

    @Column(name="duration", nullable = false)
    private String duration;

    @Column(name="availability", nullable = false)
    private String availability;

    @Column(name="accomplishments")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String [] accomplishments;

    @JsonIgnore
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name="user_id")
    private UserEntity user;

}
