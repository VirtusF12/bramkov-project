package com.bramkov.user_microservice.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Could not found user " + id);
    }

    public UserNotFoundException(String username) {
        super("Could not found user " + username);
    }
}
