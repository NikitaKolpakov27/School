package com.company.service;

import com.company.Main;
import com.company.model.Klass;
import com.company.model.Student;

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

    //TODO: Вместо цифр должны выводиться названия предметов!!!
    //TODO: Возможно, следует поменять местами Integer и List<> для удобства
    public void getStringSchedule(int klassID) {
        var schedule = getSchedule(klassID);
        System.out.println("Расписание для " + klasses.get(klassID).name + ":");
        for (var pair : schedule.entrySet()) {
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }

    public void editSchedule(Klass klass, HashMap<Integer, List<GregorianCalendar>> schedule) {
        klass.schedule = schedule;
    }

    public void removeStudent(Student student, int klassID) {
        this.klasses.get(klassID).removeStudent(student);
    }

    public void addStudent(int klassID, Student student) {
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

                if (Integer.parseInt(resultSet.getString(2)) == pair.getKey()) {
                    addStudent(
                            Integer.parseInt(resultSet.getString(2)),
                            new Student((resultSet.getString(3)), resultSet.getString(4),
                                    resultSet.getString(5), Integer.parseInt(resultSet.getString(2)),
                                    Integer.parseInt(resultSet.getString(1)))
                            );
                }
            }
        }
    }

    private void setSchedule() throws SQLException {
        ResultSet resultSet = TestConnection.statement.executeQuery("Select * from school.schedule");
        System.out.println("Check: ");
        List<GregorianCalendar> dates = new ArrayList<>();

        while (resultSet.next()) {
            dates.add(
                    new GregorianCalendar(
                        Integer.parseInt(resultSet.getString(5)),
                        Integer.parseInt(resultSet.getString(4)) - 1,
                        Integer.parseInt(resultSet.getString(3))
                        )
            );
            this.klasses.get(Integer.parseInt(resultSet.getString(1))).schedule.put(
                    Integer.parseInt(resultSet.getString(2)), dates
            );
        }
    }
}
