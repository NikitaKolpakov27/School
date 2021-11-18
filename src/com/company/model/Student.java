package com.company.model;

import com.company.service.KlassManager;
import com.company.service.SubjectManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Student {
    public String name;
    public String surname;
    public String patronymic;
    public HashMap<Integer, List<Short>> grades;
    public int klassId;
    public int studID;

    public Student(String name, String surname, String patronymic, int klassId, int studID) throws SQLException {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.klassId = klassId;
        this.grades = new HashMap<>();
        this.studID = studID;
    }

    @Override
    public String toString() {
        return this.surname + " " + this.name + " " + this.patronymic;
    }

    public String toNewString() {
        return this.surname + " " + this.name + " " + this.patronymic + " " + "ID = " + this.studID +
                "  classID = " + this.klassId;
    }

}
