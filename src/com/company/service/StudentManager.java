package com.company.service;

import com.company.Main;
import com.company.model.Klass;
import com.company.model.Student;
import com.company.model.Subject;
import com.company.other.New;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StudentManager {
    public final Map<Integer, Student> students;

    public StudentManager() throws SQLException {

        ResultSet resultSet = TestConnection.statement.executeQuery("Select * from school.students");
        List<Student> studentList = new ArrayList<>();
        while (resultSet.next()) {

            studentList.add(
                    new Student(resultSet.getString(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getInt(2),
                            resultSet.getInt(1))
            );
        }

        this.students = studentList.stream().collect(Collectors.toMap(
                student -> student.studID,
                Function.identity()
        ));

        setGrades();
    }

    public HashMap<String, List<Short>> getStudentGrades(int studID) {
        HashMap<String, List<Short>> new_grades = new HashMap<>();
        for (Map.Entry<Integer, List<Short>> pair : this.students.get(studID).grades.entrySet()) {
            //todo: не очень удобно
            if (pair.getKey() == 0) {
                new_grades.put("Русский язык", pair.getValue());
            }

            if (pair.getKey() == 1) {
                new_grades.put("Математика", pair.getValue());
            }
        }
        return new_grades;
    }

    public void addGradeBySubject(short new_grade, int subjectID, int studID) {

        //TODO: ЭТОГО НЕ БЫЛО! ОНО САМО!!!!!!!!!!!!!!!
        HashMap<Integer, List<Short>> grades;

        try {
            grades = this.students.get(studID).grades;
        } catch (NullPointerException e) {
            grades = new HashMap<>();
        }
        //TODO

        List<Short> grades_wo_subj;

        try {
            grades_wo_subj = new ArrayList<>(grades.get(subjectID));
        } catch (NullPointerException e) {
            grades_wo_subj = new ArrayList<>();
        }

        grades_wo_subj.add(new_grade);
        grades.put(subjectID, grades_wo_subj);
    }

    public void setStudentGrades(HashMap<Integer, List<Short>> grades, int studID) {
        this.students.get(studID).grades = grades;
    }

//    private void setGrades() throws SQLException {
//        ResultSet resultSet = TestConnection.statement.executeQuery("Select * from school.newgrades");
//        while (resultSet.next()) {
//
//            addGradeBySubject(
//                    Short.parseShort(resultSet.getString(4)),
//                    Integer.parseInt(resultSet.getString(3)),
//                    Integer.parseInt(resultSet.getString(1))
//            );
//
//        }
//        System.out.println("====");
//        resultSet.close();
//    }

    @New
    private void setGrades() throws SQLException {
        ResultSet resultSet = TestConnection.statement.executeQuery("Select * from test_school.grades");
        while (resultSet.next()) {
            int subjID = resultSet.getInt(2);
            int studID = resultSet.getInt(3);
            List<String> grades = Arrays.asList(resultSet.getString(4).split(","));

            for (String str : grades) {
                addGradeBySubject(
                        Short.parseShort(str),
                        subjID,
                        studID
                );
            }

        }
        resultSet.close();
    }

}
