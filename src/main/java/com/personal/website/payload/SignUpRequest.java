package com.personal.website.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class SignUpRequest
{
    @NotBlank
    @Size(min = 4, max = 40)
    private String firstName;

    @NotBlank
    @Size(min = 4, max = 40)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 15)
    private String userName;

    @NotBlank
    @Size(min = 5, max = 20)
    private String password;

    private String email;

    private String sex;

    public SignUpRequest(@NotBlank @Size(min = 3, max = 15) String userName, @NotBlank @Size(min = 5, max = 20) String password, String email)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
}
