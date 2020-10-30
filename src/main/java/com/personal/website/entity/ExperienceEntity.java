package com.personal.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.personal.website.utils.YearsCalculator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name="experience")
@Table(name="experience")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExperienceEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name="id")
    private Long id;

    @Column(name="exp_name", unique = true)
    private String name;

    @Column(name="beganOn")
    private LocalDate beganOn;

    @Column(name="years")
    private int years;

    @Column(name="months")
    private int months;

    public int getYears()
    {
        this.years = YearsCalculator.calculateYears(getBeganOn(), LocalDate.now());
        return this.years;
    }

    public  int getMonths()
    {
        this.months = YearsCalculator.calculateMonths(getBeganOn(), LocalDate.now());
        return this.months;
    }

    @ManyToOne(targetEntity = UserEntity.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserEntity user;
}
