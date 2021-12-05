package com.company.service;

import com.company.Main;
import com.company.model.Klass;
import com.company.model.Student;
import com.company.model.User;
import com.company.other.New;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KlassManager {
    public final Map<Integer, Klass> klasses;

    public KlassManager() throws SQLException {

        ResultSet resultSet = TestConnection.statement.executeQuery("Select * from school.klasses");
        List<Klass> klassesList = new ArrayList<>();
        while (resultSet.next()) {

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
        //TestConnection.connection.close();
    }

    public void removeKlass(Klass klass) throws SQLException {
        TestConnection.connection.setAutoCommit(false);
        this.klasses.remove(klass.klassId);
        TestConnection.statement.executeUpdate("Delete from school.klasses where klassID = " + klass.klassId);
        TestConnection.connection.commit();
        TestConnection.connection.setAutoCommit(true);
    }


    public void addKlass(Klass klass) throws SQLException {
        TestConnection.connection.setAutoCommit(false);
        int newId = this.klasses.size();
        this.klasses.put(newId, klass);

        String sql = "INSERT INTO school.klasses (klassID, name) VALUES (?, ?)";

        PreparedStatement preparedStatement = TestConnection.connection.prepareStatement(sql);
        preparedStatement.setInt(1, klass.klassId);
        preparedStatement.setString(2, klass.name);
        preparedStatement.executeUpdate();
        TestConnection.connection.commit();
        TestConnection.connection.setAutoCommit(true);
    }

    public void renameKlass(String name, int klassID) throws SQLException {
        TestConnection.connection.setAutoCommit(false);
        Klass klass = this.klasses.get(klassID);
        if (klass.getStudents().size() > 30) {
            klass.name = name;
            TestConnection.statement.executeUpdate("Update school.klasses Set name = " + name);
            TestConnection.connection.commit();
            TestConnection.connection.setAutoCommit(true);
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
        TestConnection.connection.setAutoCommit(false);
        this.klasses.get(klassID).removeStudent(student);
        TestConnection.statement.executeUpdate("Delete from school.students where studID = " + student.studID);
        TestConnection.connection.commit();
        TestConnection.connection.setAutoCommit(true);

        //TestConnection.connection.close();
    }

    public void addStudent(Student student, int klassID) throws SQLException {
        TestConnection.connection.setAutoCommit(false);
        this.klasses.get(klassID).addStudent(student);
        String sql = "INSERT INTO school.students (studID, classID, name, surname, patronymic) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = TestConnection.connection.prepareStatement(sql);
        preparedStatement.setInt(1, student.studID);
        preparedStatement.setInt(2, klassID);
        preparedStatement.setString(3, student.name);
        preparedStatement.setString(4, student.surname);
        preparedStatement.setString(5, student.patronymic);
        preparedStatement.executeUpdate();
        TestConnection.connection.commit();
        TestConnection.connection.setAutoCommit(true);

       //TestConnection.connection.close();
    }

    private void addStudent_inner(Student student, int klassID) throws SQLException {
        this.klasses.get(klassID).addStudent(student);
    }

    public List<Student> getStudents(int klassID) {
        Klass klass = this.klasses.get(klassID);
        return klass.getStudents();
    }

    private void setStudents() throws SQLException {
        TestConnection.connection.setAutoCommit(false);
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
        TestConnection.connection.commit();
        TestConnection.connection.setAutoCommit(true);
        //TestConnection.connection.close();
    }

//    private void setSchedule() throws SQLException {
//        TestConnection.connection.setAutoCommit(false);
//        ResultSet resultSet = TestConnection.statement.executeQuery("Select * from school.schedule");
//        List<GregorianCalendar> dates = new ArrayList<>();
//        HashMap<Integer, List<GregorianCalendar>> newMap = new HashMap<>();
//
//        int subjCount = 0;
//        while (resultSet.next()) {
//            int subjID = resultSet.getInt(2);
//
//            if (subjID != subjCount) {
//                dates = new ArrayList<>();
//                subjCount = subjID;
//            }
//
//            dates.add(new GregorianCalendar(
//                    resultSet.getInt(5),
//                    resultSet.getInt(4) - 1,
//                    resultSet.getInt(3))
//            );
//
//            this.klasses.get(resultSet.getInt(1)).schedule.put(
//                    subjID, dates
//            );
//            //dates = new ArrayList<>();
//
//        }
//        TestConnection.connection.commit();
//        TestConnection.connection.setAutoCommit(true);
//        //TestConnection.connection.close();
//    }

    @New
    private void setSchedule() throws SQLException {
        TestConnection.connection.setAutoCommit(false);
        ResultSet resultSet = TestConnection.statement.executeQuery("Select * from test_school.newschedule");
        List<Timestamp> dates = new ArrayList<>();
        HashMap<Integer, List<Timestamp>> newMap = new HashMap<>();

        int subjCount = 0;
        while (resultSet.next()) {
            int subjID = resultSet.getInt(3);

            if (subjID != subjCount) {
                dates = new ArrayList<>();
                subjCount = subjID;
            }

            dates.add(resultSet.getTimestamp(4));

            this.klasses.get(resultSet.getInt(2)).sched.put(
                    subjID, dates
            );

        }
        TestConnection.connection.commit();
        TestConnection.connection.setAutoCommit(true);
    }
}
