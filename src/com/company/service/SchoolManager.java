package com.company.service;

import com.company.model.School;
import com.company.model.User;

import java.util.HashMap;
import java.util.Map;

public class SchoolManager {
    School school;

    public SchoolManager(School school) {
        this.school = school;
    }

    public void addUser(User user) {
        this.school.users.add(user);
    }

    public void removeUser(User user) {
        this.school.users.remove(user);
    }

    @Deprecated
    public void addAccount(User user) {
        school.accounts.put(user.login, user.password);
    }

    public HashMap<String, String> getAccounts() {
        return school.accounts;
    }

    public void getStringAccounts() {
        if (school.accounts.isEmpty()) {
            System.out.println("Аккаунтов нет");
        }

        for (Map.Entry<String, String> pair : school.accounts.entrySet()) {
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }
}
