package com.personal.website.exception;

public class FailedToSaveProfile extends RuntimeException
{
    public FailedToSaveProfile() {
        super();
    }

    public FailedToSaveProfile(String message) {
        super(message);
    }

}
