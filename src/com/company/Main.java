package com.company;

import com.company.model.*;
import com.company.service.*;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static KlassManager klassManager;

    static {
        try {
            klassManager = new KlassManager();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static StudentManager studentManager;

    static {
        try {
            studentManager = new StudentManager();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static SubjectManager subjectManager;

    static {
        try {
            subjectManager = new SubjectManager();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static UserManager userManager;

    static {
        try {
            userManager = new UserManager();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void createSchedule_7a() throws SQLException {
        KlassManager klassManager = new KlassManager();
        StudentManager studentManager = new StudentManager();

        List<GregorianCalendar> list_7a_russian = new ArrayList<>();
//        list_7a_russian.add(new GregorianCalendar(2021, 8, 12));

        list_7a_russian.add(new GregorianCalendar(2021, 8, 6));
        list_7a_russian.add(new GregorianCalendar(2021, 8, 8));
        list_7a_russian.add(new GregorianCalendar(2021, 8, 9));

        List<GregorianCalendar> list_7a_math = new ArrayList<>();
        list_7a_math.add(new GregorianCalendar(2021, 8, 6));
        list_7a_math.add(new GregorianCalendar(2021, 8, 7));
        list_7a_math.add(new GregorianCalendar(2021, 8, 10));

        List<GregorianCalendar> list_7a_sports = new ArrayList<>();
        list_7a_sports.add(new GregorianCalendar(2021, 8, 6));
        list_7a_sports.add(new GregorianCalendar(2021, 8, 7));
        list_7a_sports.add(new GregorianCalendar(2021, 8, 10));

        List<GregorianCalendar> list_7a_biology = new ArrayList<>();
        list_7a_biology.add(new GregorianCalendar(2021, 8, 6));
        list_7a_biology.add(new GregorianCalendar(2021, 8, 10));

        HashMap<Integer, List<GregorianCalendar>> schedule = new HashMap<>();
        schedule.put(0, list_7a_russian);
        schedule.put(1, list_7a_math);
        schedule.put(2, list_7a_sports);
        schedule.put(3, list_7a_biology);

//        klassManager.klasses.get(0).addStudent(studentManager.students.get(0));
//        klassManager.klasses.get(0).addStudent(studentManager.students.get(1));
//        klassManager.klasses.get(0).addStudent(studentManager.students.get(2));
//
//        klassManager.editSchedule(klassManager.klasses.get(0), schedule);


        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        HashMap<String, List<Subject>> newMap = new HashMap<>();
        for (Map.Entry<Integer, List<GregorianCalendar>> pair : schedule.entrySet()) {
            List<String> dateList = new ArrayList<>();
            for (int i = 0; i < pair.getValue().size(); i++) {
                dateList.add(df.format(pair.getValue().get(i).getTime()));
//                System.out.println(subjectManager.subjects.get(pair.getKey()) + " = " + df.format(pair.getValue().get(i).getTime()));
            }
            System.out.println(subjectManager.subjects.get(pair.getKey()) + " = " + dateList);
        }

//        for (Map.Entry<Integer, List<GregorianCalendar>> pair : schedule.entrySet()) {
//            List<Subject> subjectList = new ArrayList<>();
//            for (int i = 0; i < pair.getValue().size(); i++) {
//                System.out.println(subjectManager.subjects.get(pair.getKey()) + " = " + df.format(pair.getValue().get(i).getTime()));
//                subjectList.add(subjectManager.subjects.get(pair.getKey()));
//                newMap.put(df.format(pair.getValue().get(i).getTime()), subjectList);
//            }
//        }

//        System.out.println();
//        for (Map.Entry<String, List<Subject>> pair : newMap.entrySet()) {
//            System.out.println(pair.getKey() + " = " + pair.getValue());
//        }


    }

    public static void createGrades() {
        HashMap<Integer, List<Short>> grades_apa = new HashMap<>();
        HashMap<Integer, List<Short>> grades_adf = new HashMap<>();


        //???????????? ?????????????? ?????????????? ???? ???????????????? (subjID=0)
        List<Short> grades_apa_rus = List.of( (short) 4, (short) 5, (short) 5, (short) 4);
        grades_apa.put(0, grades_apa_rus);

        //???????????? ?????????????? ?????????????? ???? ???????????????????? (subjID=1)
        List<Short> grades_apa_math = List.of( (short) 4, (short) 4, (short) 3, (short) 3);
        grades_apa.put(1, grades_apa_math);

        //???????????? ?????????? ?????????????????????? ???? ???????????????? (subjID=0)
        List<Short> grades_adf_rus = List.of( (short) 5, (short) 5, (short) 5, (short) 5);
        grades_adf.put(0, grades_adf_rus);

        //???????????? ?????????? ?????????????????????? ???? ???????????????????? (subjID=1)
        List<Short> grades_adf_math = List.of( (short) 4, (short) 4, (short) 5, (short) 3);
        grades_adf.put(1, grades_adf_math);

        studentManager.setStudentGrades(grades_adf, 1);
        studentManager.setStudentGrades(grades_apa, 0);
    }

    public static void demonstration() throws SQLException {
        //?????????? ?????????????????? 7??
        System.out.println("?????????????? " + klassManager.klasses.get(0).name + " ????????????:");
        System.out.println("-----------------");
        for (Student student : klassManager.getStudents(0)) {
            System.out.println(student.toString());
        }
        System.out.println("-----------------");

        //?????????? ?????????????????? 8??
        System.out.println("?????????????? " + klassManager.klasses.get(1).name + " ????????????:");
        System.out.println("-----------------");
        for (Student student : klassManager.getStudents(1)) {
            System.out.println(student.toString());
        }
        System.out.println("-----------------");

        //?????????? ?????????????????? (????????)
        System.out.println("?????? ?????????????? ??????????:");
        System.out.println("-----------------");
        for (Map.Entry<Integer, Student> pair: studentManager.students.entrySet()) {
            System.out.println("ID = " + pair.getKey() + "; " + pair.getValue().toString());
        }
        System.out.println("-----------------");


        System.out.println("???????????? ????????????????(?????? ?????????????????????? ???? ????????????):");
        System.out.println("-----------------");
        for (Map.Entry<Integer, Student> pair: studentManager.students.entrySet()) {

            String str = "????????????: ";
            if (pair.getValue().grades.entrySet().size() == 0) {
                str += "???????? ?????? ????????????.";
            }

            for (Map.Entry<Integer, List<Short>> par : pair.getValue().grades.entrySet()) {
                str += subjectManager.subjects.get(par.getKey()) + " = " + par.getValue();
                str += " ";
            }

            System.out.println("ID = " + pair.getKey() + "; " + pair.getValue().toString() + "; " +
                    str);
        }
        System.out.println("-----------------");

        System.out.println("????????????????:");
        System.out.println("-----------------");
        for (Map.Entry<Integer, Subject> pair: subjectManager.subjects.entrySet()) {
            System.out.println("ID = " + pair.getKey() + "; " + pair.getValue().toString() + ";");
        }
        System.out.println("-----------------");

        Map<Integer, List<Timestamp>> schedule = klassManager.getSchedule(0);

        // new, with timestamp
        System.out.println("???????????????????? (?????? 7??): ");
        System.out.println("---------------------");
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        for (Map.Entry<Integer, List<Timestamp>> pair : schedule.entrySet()) {
            List<String> dateList = new ArrayList<>();
            for (int i = 0; i < pair.getValue().size(); i++) {
                dateList.add(df.format(pair.getValue().get(i).getTime()));
            }
            System.out.println(subjectManager.subjects.get(pair.getKey()) + " = " + dateList);
        }
        System.out.println("---------------------");

        //???????????????? ????????????????
//        System.out.println("???????????????? ???????????????? ???? 7?? (): ");
//        System.out.println("---------------------");
//        Student removingStudent = klassManager.getStudents(0).get(0);
//        System.out.println("?????????????????? ??????????????: " + removingStudent.toString());
//        klassManager.removeStudent(removingStudent, 0);
//        System.out.println("---------------------");

        //?????????? ?????????????????? 8??
        System.out.println("?????????????? " + klassManager.klasses.get(1).name + " ????????????:");
        System.out.println("-----------------");
        for (Student student : klassManager.getStudents(1)) {
            System.out.println(student.toString());
        }
        System.out.println("-----------------");

        //???????????????????? ????????????????
//        System.out.println("???????????????????? ???????????????? ?? 8?? (): ");
//        System.out.println("---------------------");
//        int newID = studentManager.students.size();
//        Student addingStudent = new Student("??????????????", "????????????", "????????????????????", 1, newID);
//        System.out.println("?????????????????????? ??????????????: " + addingStudent.toString());
//        klassManager.addStudent(addingStudent, 1);
//        System.out.println("---------------------");

        //?????????? ?????????????????? 7??
        System.out.println("?????????????? " + klassManager.klasses.get(0).name + " ????????????:");
        System.out.println("-----------------");
        for (Student student : klassManager.getStudents(0)) {
            System.out.println(student.toString());
        }
        System.out.println("-----------------");


//        //?????????????????????? ????????????
//        Student stud = studentManager.students.get(0);
//        studentManager.addGradeBySubject((short)2, 0, 2);
//        System.out.println();
//        //
//
//        System.out.println("???????????? ????????????????(?????? ?????????????????????? ???? ????????????):");
//        System.out.println("-----------------");
//        for (Map.Entry<Integer, Student> pair: studentManager.students.entrySet()) {
//
//            String str = "????????????: ";
//            if (pair.getValue().grades.entrySet().size() == 0) {
//                str += "???????? ?????? ????????????.";
//            }
//
//            for (Map.Entry<Integer, List<Short>> par : pair.getValue().grades.entrySet()) {
//                str += subjectManager.subjects.get(par.getKey()) + " = " + par.getValue();
//                str += " ";
//            }
//
//            System.out.println("ID = " + pair.getKey() + "; " + pair.getValue().toString() + "; " +
//                    str);
//        }
//        System.out.println("-----------------");
    }



    public static void main(String[] args) throws IOException, SQLException {
//        demonstration();
        Authorisation authorisation = new Authorisation();
        authorisation.greetings();
    }
}
