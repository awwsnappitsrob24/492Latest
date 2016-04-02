package com.example.robien.beachbuddy;

/**
 * Created by Robien on 4/2/2016.
 */
public class Invite {

    private String classType;

    public Invite(String classType) {
        this.setClassType(classType);
    }

    public String getClassInvite() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}
