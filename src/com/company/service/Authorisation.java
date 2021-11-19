package com.company.service;

import com.company.model.School;
import com.company.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Authorisation {//TODO: СПРОСИТЬ, СТОИТ ЛИ МЕТОДЫ В ЭТОМ КЛАССЕ ДЕЛАТЬ СТАТИЧЕСКИМИ!!!

    static UserManager userManager;

    static {
        try {
            userManager = new UserManager();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void greetings() throws IOException, SQLException {

        System.out.println("Войдите или зарегестрируйтесь");
        System.out.println("Введите \"r\", чтобы зарегестрироваться, и \"in\", чтобы войти.");
        System.out.println("--------------------------");
        System.out.println("Ввод: ");
        Scanner scn = new Scanner(System.in);
        String answer = scn.nextLine();

        if (answer.equals("r")) {
            registerUser();
        } else if (answer.equals("in")) {
            loginUser();
        } else {
            System.out.println("Please, check the sentence you just wrote");
        }
    }

    public void registerUser() throws IOException, SQLException {
        System.out.println("----REGISTRATION PROCESS----");
        System.out.println("Введите логин: ");
        Scanner scn = new Scanner(System.in);
        String login = scn.nextLine();

        if (userManager.getAccounts().containsKey(login)) {
            System.out.println("Этот логин занят");
            return;
        }

        System.out.println("Введите пароль: ");
        String password = scn.nextLine();
        int newId = userManager.users.size();

        User new_user = new User(newId, login, password);
        userManager.addUser(new_user);

        System.out.println("Вы успешно зарегестрировались!");
    }

    public void loginUser() {
        System.out.println("----SIGN-IN PROCESS----");
        System.out.println("Введите логин: ");
        Scanner scn = new Scanner(System.in);
        String login = scn.nextLine();
        System.out.println("Введите пароль: ");
        String password = scn.nextLine();


        if (!userManager.getAccounts().containsKey(login) || !userManager.getAccounts().containsValue(password)) {
            System.out.println("Неправильный логин или пароль");
        } else {
            System.out.println("Вы успешно вошли!");
        }
    }
}
