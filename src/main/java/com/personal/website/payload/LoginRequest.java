package com.personal.website.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest
{
    @NotBlank
    private String userNameOrEmail;

    @NotBlank
    private String password;

    public LoginRequest(String userNameOrEmail, String password)
    {
        this.userNameOrEmail = userNameOrEmail;
        this.password = password;
    }

    public LoginRequest() {
    }

    public String getUserName()
    {
        return userNameOrEmail;
    }

    public void setUserName(String userNameOrEmail)
    {
        this.userNameOrEmail = userNameOrEmail;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
