package com.company.service;

import com.company.Main;
import com.company.model.Klass;
import com.company.model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KlassManager {
    public final Map<Integer, Klass> klasses;

    public KlassManager() throws SQLException {
//        this.klasses = List.of(klass_7a, klass_8b).stream().collect(Collectors.toMap(
//                klass -> klass.klassId,
//                Function.identity()
//        ));

        ResultSet resultSet = TestConnection.statement.executeQuery("Select * from school.klasses");
        List<Klass> klassesList = new ArrayList<>();
        while (resultSet.next()) {

            //added at 14.11
            klassesList.add(
                    new Klass(resultSet.getString(2), Integer.parseInt(resultSet.getString(1)))
            );
        }

        this.klasses = klassesList.stream().collect(Collectors.toMap(
                klass -> klass.klassId,
                Function.identity()
        ));

        setStudents();
        setSchedule();
    }

    public void renameKlass(String name, int klassID) {
        Klass klass = this.klasses.get(klassID);
        if (klass.getStudents().size() > 30) {
            klass.name = name; //мб пометить name protected и связать классМенеджер и класс
        } else {
            System.out.println("It's not necessary to rename this class.");
        }
    }

    public Map<Integer, List<GregorianCalendar>> getSchedule(int klassID) {
        return this.klasses.get(klassID).schedule;
    }


    public void getStringSchedule(int klassID) {
        var schedule = getSchedule(klassID);
        System.out.println("Расписание для " + klasses.get(klassID).name + ":");
        for (var pair : schedule.entrySet()) {
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }

    public void editSchedule(int klassID, HashMap<Integer, List<GregorianCalendar>> schedule) {
        this.klasses.get(klassID).schedule = schedule;
    }

    public void removeStudent(Student student, int klassID) throws SQLException {
        this.klasses.get(klassID).removeStudent(student);
        TestConnection.statement.executeUpdate("Delete from school.students where studID = " + student.studID);
    }

    public void addStudent(Student student, int klassID) throws SQLException {
        this.klasses.get(klassID).addStudent(student);
        String sql = "INSERT INTO school.students (studID, classID, name, surname, patronymic) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = TestConnection.connection.prepareStatement(sql);
        preparedStatement.setInt(1, student.studID);
        preparedStatement.setInt(2, klassID);
        preparedStatement.setString(3, student.name);
        preparedStatement.setString(4, student.surname);
        preparedStatement.setString(5, student.patronymic);
        preparedStatement.executeUpdate();
    }

    private void addStudent_inner(Student student, int klassID) throws SQLException {
        this.klasses.get(klassID).addStudent(student);
    }

    public List<Student> getStudents(int klassID) {
        Klass klass = this.klasses.get(klassID);
        return klass.getStudents();
    }

    private void setStudents() throws SQLException {
        for (Map.Entry<Integer, Klass> pair : klasses.entrySet()) {
            ResultSet resultSet = TestConnection.statement.executeQuery("Select * from school.students");
            while (resultSet.next()) {

                if (resultSet.getInt(2) == pair.getKey()) {
                    addStudent_inner(
                            new Student((resultSet.getString(3)), resultSet.getString(4),
                                    resultSet.getString(5), resultSet.getInt(2),
                                    resultSet.getInt(1)),
                            resultSet.getInt(2)
                            );
                }
            }
        }
    }

    private void setSchedule() throws SQLException {
        ResultSet resultSet = TestConnection.statement.executeQuery("Select * from school.schedule");
        List<GregorianCalendar> dates = new ArrayList<>();
        HashMap<Integer, List<GregorianCalendar>> newMap = new HashMap<>();

        int subjCount = 0;
        while (resultSet.next()) {
            int subjID = resultSet.getInt(2);

            if (subjID != subjCount) {
                dates = new ArrayList<>();
                subjCount = subjID;
            }

            dates.add(new GregorianCalendar(
                    resultSet.getInt(5),
                    resultSet.getInt(4) - 1,
                    resultSet.getInt(3))
            );

            this.klasses.get(resultSet.getInt(1)).schedule.put(
                    subjID, dates
            );
            //dates = new ArrayList<>();

        }
    }
}
