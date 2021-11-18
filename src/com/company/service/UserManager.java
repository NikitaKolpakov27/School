package com.company.service;

import com.company.model.User;

public class UserManager {
    User user;

    public UserManager(User user) {
        this.user = user;
    }

    public void changeLogin(String new_login) {
        this.user.login = new_login;
    }

    public void changePassword(String new_password) {
        this.user.password = new_password;
    }
}
