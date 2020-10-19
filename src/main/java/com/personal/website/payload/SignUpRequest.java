package com.personal.website.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
public class SignUpRequest
{
    @Size(min = 4, max = 40)
    private String firstName;

    @Size(min = 4, max = 40)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 15)
    private String userName;

    @NotBlank
    @Size(min = 5, max = 20)
    private String password;

    @NotBlank
    private String email;

    private String sex;

    private boolean isOnline;

    private int age;

    private LocalDate dateOfBirth;

    public SignUpRequest()
    {}

    public SignUpRequest(@Size(min = 4, max = 40)
                                 String firstName,
                         @Size(min = 4, max = 40)
                                 String lastName,
                         @NotBlank @Size(min = 3, max = 15)
                                 String userName,
                         @NotBlank @Size(min = 5, max = 20)
                                 String password,
                         @NotBlank
                                 String email,
                         String sex)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.sex = sex;
    }

    public SignUpRequest(@NotBlank @Size(min = 3, max = 15) String userName, @NotBlank @Size(min = 5, max = 20) String password, String email)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }


}
