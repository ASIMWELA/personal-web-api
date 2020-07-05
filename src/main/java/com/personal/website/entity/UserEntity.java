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
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="user_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name="id", nullable = false, length = 11)
    private Long id;

    @Column(length = 10, unique = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String uid;

    @Column(name="first_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    @Column(name="last_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @Column(name="user_name", unique = true,length = 50)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userName;

    @Column(name="email", unique = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email, please check your email")
    private String email;

    @Column(name="password")
    @Pattern(regexp = "((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{6,16})")
    private String password;

    @Column(name="sex", length = 20)
    private String sex;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="contact_info_id", referencedColumnName = "contact_info_id")
    private ContactInfoEntity contactInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="profile_id", referencedColumnName = "profile_id")
    private ProfilePictureEntity profilePicture;

}
