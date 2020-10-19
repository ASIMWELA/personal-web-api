package com.personal.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.website.utils.YearsCalculator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Entity(name="user")
@Table(name="user_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name="id", nullable = false, length = 11)
    private Long id;

    @Column(name="uid", length = 15, unique = true)
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private String password;

    @Column(name="sex", length = 20)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sex;

    @Column(name="is_online")
    private boolean isOnline ;

    @Column(name="age")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private int age ;

    @Column(name="dob")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate dateOfBirth;

    public UserEntity(String uid, String firstName, String lastName, String userName, @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email, please check your email") String email, @Pattern(regexp = "(?-i)(?=^.{5,}$)((?!.*\\s)(?=.*[A-Z])(?=.*[a-z]))((?=(.*\\d){1,})|(?=(.*\\W){1,}))^.*$") String password, String sex, LocalDate dateOfBirth, boolean isOnline)
    {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.dateOfBirth=dateOfBirth;
        this.isOnline = isOnline;
        this.sex = sex;
    }


    public UserEntity(String uid,String userName, @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email, please check your email") String email, @Pattern(regexp = "(?-i)(?=^.{5,}$)((?!.*\\s)(?=.*[A-Z])(?=.*[a-z]))((?=(.*\\d){1,})|(?=(.*\\W){1,}))^.*$") String password, boolean isOnline)
    {   this.uid = uid;
        this.userName = userName;
        this.email = email;
        this.isOnline = isOnline;
        this.password = password;
    }

    public int getAge()
    {
        this.age = YearsCalculator.calculateYears(getDateOfBirth(), LocalDate.now());
        return this.age;
    }

    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JoinColumn(name="contact_info_id", referencedColumnName = "contact_info_id")
    private ContactInfoEntity contactInfo;


    @OneToMany(mappedBy = "user",cascade = {CascadeType.REMOVE})
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ExperienceEntity> experience;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.REMOVE})
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<EmploymentEntity> employment;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.REMOVE})
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SkillEntity> skills;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.REMOVE})
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<EducationEntity> education;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<RoleEntinty> roles ;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JoinColumn(name="profile_id", referencedColumnName = "profile_id")
    private ProfilePictureEntity profilePicture;


}
