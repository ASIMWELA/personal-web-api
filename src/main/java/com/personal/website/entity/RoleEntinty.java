package com.personal.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.personal.website.model.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name="roles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RoleEntinty
{
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 20)
    private ERole name;
}
