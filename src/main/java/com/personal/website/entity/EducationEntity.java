package com.personal.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity(name="education")
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
    @ManyToOne(targetEntity = UserEntity.class,cascade = CascadeType.REMOVE)
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;
}
