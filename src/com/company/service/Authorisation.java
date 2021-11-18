package com.company.service;

import com.company.model.School;
import com.company.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Authorisation { //TODO: СПРОСИТЬ, СТОИТ ЛИ МЕТОДЫ В ЭТОМ КЛАССЕ ДЕЛАТЬ СТАТИЧЕСКИМИ!!!

//    public static School school = new School();

    public void greetings(School school) throws IOException {
        SchoolManager schoolManager = new SchoolManager(school);
        System.out.println("Войдите или зарегестрируйтесь");
        System.out.println("Введите \"r\", чтобы зарегестрироваться, и \"in\", чтобы войти.");
        System.out.println("--------------------------");
        System.out.println("Ввод: ");
        Scanner scn = new Scanner(System.in);
        String answer = scn.nextLine();
        if (answer.equals("r")) {
            registerUser(schoolManager);
        } else if (answer.equals("in")) {
            loginUser(schoolManager);
        } else {
            System.out.println("Please, check the sentence you just wrote");
        }
    }

    public void registerUser(SchoolManager schoolManager) throws IOException {
        System.out.println("----REGISTRATION PROCESS----");
        System.out.println("Введите логин: ");
        Scanner scn = new Scanner(System.in);
        String login = scn.nextLine();

        if (schoolManager.getAccounts().containsKey(login)) {
            System.out.println("Этот логин занят");
            return;
        }

        System.out.println("Введите пароль: ");
        String password = scn.nextLine();

        User new_user = new User(login, password);
        schoolManager.addUser(new_user);

        File file = new File("C:\\Users\\Колпаков Сергей\\IdeaProjects\\School\\src\\com\\company\\school_db");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(new_user.login + "-" + new_user.password);
        fileWriter.flush();
        fileWriter.close();
//        schoolManager.addAccount(new_user);
        System.out.println("Вы успешно зарегестрировались!");
    }

    public void loginUser(SchoolManager schoolManager) {
        System.out.println("----SIGN-IN PROCESS----");
        System.out.println("Введите логин: ");
        Scanner scn = new Scanner(System.in);
        String login = scn.nextLine();
        System.out.println("Введите пароль: ");
        String password = scn.nextLine();

//        if (!school.accounts.containsKey(login) || !school.accounts.containsValue(password)) {
        if (!schoolManager.getAccounts().containsKey(login) || !schoolManager.getAccounts().containsValue(password)) {
            System.out.println("Неправильный логин или пароль");
        } else {
            System.out.println("Вы успешно вошли!");
        }
    }
}
