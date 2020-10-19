package com.personal.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="skills")
@Table(name="skills")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SkillEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(name="tech", nullable = false, unique = true)
    private String technology;

    @Column(name="skills", nullable = false)
    private String[] skills;

    @JsonIgnore
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name="user_id")
    private UserEntity user;


}
