package com.company.service;

import com.company.Main;
import com.company.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public void loginUser() throws SQLException {
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

    private void mainProcess(String login) throws SQLException {
        System.out.println("Здравствуйте, " + login + "! " + "Выберете, что хотите сделать:");

        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.println("----ГЛАВНОЕ МЕНЮ----");
            System.out.println("1. Вывести студентов;");
            System.out.println("2. Вывести оценки студентов;");
            System.out.println("3. Вывести списки предметов;");
            System.out.println("4. Получить расписание класса;");
            System.out.println("5. Добавить студента;");
            System.out.println("6. Удалить студента;");
            System.out.println("7. Добавить оценку студенту;");
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
                case 3:
                    getSubjects();
                    break;
                case 4:
                    getKlassSchedule();
                    break;
                case 5:
                    addNewStudent();
                    break;
                case 6:
                    removeStudent();
                    break;
                default:
                    System.out.println("Неправильный ввод");
                    break;
            }
        }
    }

    private void getSubjects() {
        System.out.println("Предметы:");
        System.out.println("-----------------");
        for (Map.Entry<Integer, Subject> pair: Main.subjectManager.subjects.entrySet()) {
            System.out.println("ID = " + pair.getKey() + "; " + pair.getValue().toString() + ";");
        }
        System.out.println("-----------------");
        System.out.println();
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

    private void getKlassSchedule() {
        System.out.println("Расписание какого класса Вам необходимо получить?");

        for (Map.Entry<Integer, Klass> pair : Main.klassManager.klasses.entrySet()) {
            System.out.println((pair.getKey() + 1) +  ". " + pair.getValue().name);
        }
        System.out.println("-1. Выход;");

        Scanner scn = new Scanner(System.in);
        System.out.println("Введите номер: ");
        int num = scn.nextInt();
        System.out.println();

        if (num >= 0 && num <= Main.klassManager.klasses.entrySet().size()) {
            getKlassScheduleByKlassID(num - 1);
        } else if (num == -1) {
            System.out.println("Работа с расписанием класса завершена!");
        } else if (num > Main.klassManager.klasses.entrySet().size()) {
            System.out.println("Неправильный ввод!");
        }
    }

    private void getKlassScheduleByKlassID(int klassID) {
        Map<Integer, List<GregorianCalendar>> schedule = Main.klassManager.getSchedule(klassID);

        if (schedule.isEmpty()) {
            System.out.println("Расписание для этого класса ещё не составлено.");
            return;
        }

        System.out.println("Расписание для " + Main.klassManager.klasses.get(klassID).name + ":");
        System.out.println("---------------------");
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        for (Map.Entry<Integer, List<GregorianCalendar>> pair : schedule.entrySet()) {
            List<String> dateList = new ArrayList<>();
            for (int i = 0; i < pair.getValue().size(); i++) {
                dateList.add(df.format(pair.getValue().get(i).getTime()));
            }
            System.out.println(Main.subjectManager.subjects.get(pair.getKey()) + " = " + dateList);
        }
        System.out.println("---------------------");
        System.out.println();
    }

// need to check!!!
    private void addNewStudent() throws SQLException {
        System.out.println("Введите данные нового студента: ");
        System.out.println("*-*-*-*-*");
        Scanner scn = new Scanner(System.in);
        String name, surname, patronymic;
        int klassID;

        System.out.println("Имя: ");
        name = scn.nextLine();
        System.out.println("Фамилия: ");
        surname = scn.nextLine();
        System.out.println("Отчество: ");
        patronymic = scn.nextLine();
        System.out.println("KlassID: ");
        klassID = scn.nextInt();

        addNewStudentByKlassID(name, surname, patronymic, klassID);
    }

    private void addNewStudentByKlassID(String name, String surname, String patronymic, int klassID) throws SQLException {
        System.out.println();
        int newID = Main.studentManager.students.size();
        Student addingStudent = new Student(name, surname, patronymic, klassID, newID);
        Main.klassManager.addStudent(addingStudent, klassID);
        System.out.println("---------------------");
        System.out.println();
    }

    private void removeStudent() throws SQLException {
        System.out.println("Из какого класса удалить студента?");

        for (Map.Entry<Integer, Klass> pair : Main.klassManager.klasses.entrySet()) {
            System.out.println((pair.getKey()) +  ". " + pair.getValue().name);
        }
        System.out.println("-1. Выход;");

        Scanner scn = new Scanner(System.in);
        System.out.println("Введите номер: ");
        int num = scn.nextInt();
        System.out.println();

        if (num >= 0 && num < Main.klassManager.klasses.entrySet().size()) {
            removeStudentByKlassID(num);
        } else if (num == -1) {
            System.out.println("Работа с удалением студентов завершена");
        } else {
            System.out.println("Неправильный ввод! Класса с таким klassID не существует");
        }
    }

    private void removeStudentByKlassID(int klassID) throws SQLException {
        System.out.println("Какого студента Вы хотите удалить?");

        for (Student student : Main.klassManager.klasses.get(klassID).getStudents()) {
            System.out.println(student.studID + ". " + student.toString());
        }
        System.out.println("-1. Выход");

        Scanner scn = new Scanner(System.in);
        System.out.println("Введите номер: ");
        int num = scn.nextInt();
        System.out.println();

        if (num >= 0 && num < Main.klassManager.klasses.get(klassID).getStudents().size()) {
            Student removingStudent = Main.klassManager.getStudents(klassID).get(num);
            Main.klassManager.removeStudent(removingStudent, klassID);
        } else if (num == -1) {
            System.out.println("Работа с удалением студентов завершена");
        } else {
            System.out.println("Неправильный ввод! Студента с таким studID не существует");
        }



    }

}
