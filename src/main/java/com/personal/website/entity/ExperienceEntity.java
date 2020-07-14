package com.personal.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.website.utils.YearsCalculator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="experience")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExperienceEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name="id")
    private Long id;

    @Column(name="exp_name", unique = true)
    private String name;

    @Column(name="beganOn")
    private LocalDate beganOn;

    @Column(name="years")
    private int years;

    public int getYears()
    {
        this.years = YearsCalculator.calculateYears(getBeganOn(), LocalDate.now());
        return this.years;
    }

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private UserEntity user;
}
