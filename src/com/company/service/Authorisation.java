package com.company.service;

import com.company.Main;
import com.company.model.Klass;
import com.company.model.School;
import com.company.model.Student;
import com.company.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Authorisation {//TODO: СПРОСИТЬ, СТОИТ ЛИ МЕТОДЫ В ЭТОМ КЛАССЕ ДЕЛАТЬ СТАТИЧЕСКИМИ!!!

    static UserManager userManager;
    private boolean inSystemStatus = false;

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
            inSystemStatus = false;
            return;
        }

        System.out.println("Введите пароль: ");
        String password = scn.nextLine();
        int newId = userManager.users.size();

        User new_user = new User(newId, login, password);
        userManager.addUser(new_user);

        System.out.println("Вы успешно зарегестрировались!");
        inSystemStatus = true;
        mainProcess(login);
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
            inSystemStatus = false;
        } else {
            System.out.println("Вы успешно вошли!");
            inSystemStatus = true;
            mainProcess(login);
        }
    }

    private void mainProcess(String login) {
        System.out.println("Здравствуйте, " + login + "! " + "Выберете, что хотите сделать:");

        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.println("----ГЛАВНОЕ МЕНЮ----");
            System.out.println("1. Вывести студентов;");
            System.out.println("2. Вывести оценки студентов;");
            System.out.println("0. Выход;");
            System.out.println("---------------------");
            System.out.println("Введите номер: ");
            int num = scn.nextInt();

            if (num == 0) {
                System.out.println("До скорых встреч!");
                break;
            }

            switch (num) {
                case 1:
                    getStudents();
                    break;
                case 2:
                    getStudentsGrades();
                    break;
                default:
                    System.out.println("Неправильный ввод");
                    break;
            }
        }
    }

    private void getStudents() {

        System.out.println("Студнтов каких классов нужн вывсти?");

        for (Map.Entry<Integer, Klass> pair : Main.klassManager.klasses.entrySet()) {
            System.out.println((pair.getKey() + 1) +  ". " + pair.getValue().name);
        }
        System.out.println("0. Всх учников школы;");
        System.out.println("-1. Выход;");

        Scanner scn = new Scanner(System.in);
        System.out.println("Введите номер: ");
        int num = scn.nextInt();
        System.out.println();

        if (num >= 1 && num <= Main.klassManager.klasses.entrySet().size()) {
            getStudentsByKlassID(num - 1);
        } else if (num == 0) {
            System.out.println("Все ученики школы:");
            System.out.println("-----------------");
            for (Map.Entry<Integer, Student> pair: Main.studentManager.students.entrySet()) {
                System.out.println("ID = " + pair.getKey() + "; " + pair.getValue().toString());
            }
            System.out.println("-----------------");
        } else if (num == -1) {
            System.out.println("Рабта с студнтами завршна");
        } else if (num > Main.klassManager.klasses.entrySet().size()) {
            System.out.println("Неправильный ввод");
        }
    }

    private void getStudentsByKlassID(int klassID) {
        System.out.println("Ученики " + Main.klassManager.klasses.get(klassID).name + " класса:");
        System.out.println("-----------------");
        for (Student student : Main.klassManager.getStudents(klassID)) {
            System.out.println(student.toString());
        }
        System.out.println("-----------------");
        System.out.println();
    }

    private void getStudentsGrades() {
        System.out.println("Оценки учеников как класса вас интрсуют:");

        for (Map.Entry<Integer, Klass> pair : Main.klassManager.klasses.entrySet()) {
            System.out.println((pair.getKey() + 1) +  ". " + pair.getValue().name);
        }
        System.out.println("0. Н важн. Вс;");
        System.out.println("-1. Выход;");

        Scanner scn = new Scanner(System.in);
        System.out.println("Введите номер: ");
        int num = scn.nextInt();
        System.out.println();

        if (num >= 0 && num <= Main.klassManager.klasses.entrySet().size()) {
            getStudentsGradesByKlassID(num - 1);
        } else if (num == -1) {
            System.out.println("Рабта с цнками студнтв завршна");
        } else if (num > Main.klassManager.klasses.entrySet().size()) {
            System.out.println("Неправильный ввод");
        }
    }

    private void getStudentsGradesByKlassID(int klassID) {
        if (klassID > 0) {
            System.out.println("Оценки учеников " + Main.klassManager.klasses.get(klassID).name + " класса:");
        } else {
            System.out.println("Оценки всх учеников: ");
        }
        System.out.println("-----------------");

        for (Map.Entry<Integer, Student> pair: Main.studentManager.students.entrySet()) {

            String str = "ОЦЕНКИ: ";
            if (pair.getValue().grades.entrySet().size() == 0) {
                str += "Пока нет оценок.";
            }

            for (Map.Entry<Integer, List<Short>> par : pair.getValue().grades.entrySet()) {
                str += Main.subjectManager.subjects.get(par.getKey()) + " = " + par.getValue();
                str += " ";
            }

            if (klassID == -1) {
                System.out.println("ID = " + pair.getKey() + "; " + pair.getValue().toString() + "; " +
                        str);
            }

            else if (pair.getValue().klassId == klassID && klassID >= 0) {
                System.out.println("ID = " + pair.getKey() + "; " + pair.getValue().toString() + "; " +
                        str);
            }
        }
        System.out.println("-----------------");
        System.out.println();
    }


}
