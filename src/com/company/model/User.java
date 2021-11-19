package com.company.model;

public class User {
    public int userID;
    public String login;
    public String password;

    public User(int userID, String login, String password) {
        this.userID = userID;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "login: " + this.login + "; pass: " + this.password;
    }
}
