package com.example.PetersPetitions;

import java.util.ArrayList;

public class User {
    private String name;

    private String email;

    /**
     * constructor
     * @param name
     * @param email
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
