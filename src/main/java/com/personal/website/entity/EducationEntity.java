package com.personal.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="education")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EducationEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(name="institution", nullable = false)
    private String institution;

    @Column(name="awards", nullable = false)
    private String[] awards;

    @Column(name="period")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String period;


    @JsonIgnore
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private UserEntity user;
}
