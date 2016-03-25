package com.example.robien.beachbuddy;

/**
 * Created by Robien on 3/18/2016.
 */
public class Student {

    private String name, email;

    public Student(String name, String email) {
        this.setName(name);
        this.setEmail(email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
