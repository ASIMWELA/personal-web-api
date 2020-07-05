package com.personal.website.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="subscriber")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubscriberEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    @JsonIgnore
    private Long id;

    @Column(name="email", unique = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "The input is not a valid email")
    private String email;

    @Column(name="first_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    @Column(name="last_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @Column(name="password")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Pattern(regexp = "((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{6,16})", message = "weak password")
    private String password;
}
