package com.personal.website.payload;

import com.personal.website.model.User;

public class LoginResponse
{

    private String ACCESS_TOKEN;

    private User user;

    public LoginResponse(String ACCESS_TOKEN, User user) {
        this.ACCESS_TOKEN = ACCESS_TOKEN;
        this.user = user;
    }

    public LoginResponse() {
    }

    public String getACCESS_TOKEN() {
        return ACCESS_TOKEN;
    }

    public void setACCESS_TOKEN(String ACCESS_TOKEN) {
        this.ACCESS_TOKEN = ACCESS_TOKEN;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
