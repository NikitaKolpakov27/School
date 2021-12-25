package com.company.service;

import com.company.Main;
import com.company.model.Klass;
import com.company.model.Student;
import com.company.model.Subject;
import com.company.other.New;

import java.sql.PreparedStatement;
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

    public void addGradeBySubject(short new_grade, int subjectID, int studID) throws SQLException {
//        TestConnection.connection.setAutoCommit(false);
//        ResultSet resultSet = TestConnection.statement.executeQuery("Select grades_list from test_school.grades where " +
//                "studID = " + studID + " and subjID = " + subjectID);
//        String old_str = "";
//        while (resultSet.next()) {
//            old_str = resultSet.getString(1);
//        }

        HashMap<Integer, List<Short>> grades;

        try {
            grades = this.students.get(studID).grades;
        } catch (NullPointerException e) {
            grades = new HashMap<>();
        }

        List<Short> grades_wo_subj;

        try {
            grades_wo_subj = new ArrayList<>(grades.get(subjectID));
        } catch (NullPointerException e) {
            grades_wo_subj = new ArrayList<>();
        }

        grades_wo_subj.add(new_grade);
        grades.put(subjectID, grades_wo_subj);
//        String new_str = old_str + "," + String.valueOf(new_grade);
//
//        String sql = "UPDATE test_school.grades SET grades_list = ?";
//        PreparedStatement statement = TestConnection.connection.prepareStatement(sql);
//        statement.setString(1, new_str);
//        statement.executeUpdate();
    }

    public void setStudentGrades(HashMap<Integer, List<Short>> grades, int studID) {
        this.students.get(studID).grades = grades;
    }


    public void addFromSysToDB() throws SQLException {
        System.out.println("GRADES: ");
        for (Map.Entry<Integer, Student> pair : students.entrySet()) {
            System.out.println(pair.getValue().name + " = " + pair.getValue().grades);


//            TestConnection.connection.setAutoCommit(false);
//            ResultSet resultSet = TestConnection.statement.executeQuery("Select * from test_school.grades where " +
//                    "studID = " + 0 + " and subjID = " + 0);
//            String old_str = "";
//            int id = 0;
//            while (resultSet.next()) {
//                id = resultSet.getInt(1);
//                old_str = resultSet.getString(4);
//            }
//
//            String new_str = "'" + String.valueOf(pair.getValue().grades.values()) + "'";
//            String sql = "UPDATE test_school.grades SET grades_list = ? where gradeID = " + id;
//            PreparedStatement statement = TestConnection.connection.prepareStatement(sql);
//            statement.setString(1, new_str);
//            statement.executeUpdate();
        }
    }

    @New
    private void setGrades() throws SQLException {
        ResultSet resultSet = TestConnection.statement.executeQuery("Select * from test_school.grades");
        while (resultSet.next()) {
            int subjID = resultSet.getInt(2);
            int studID = resultSet.getInt(3);
            String grades_str = resultSet.getString(4);
            List<String> grades = Arrays.asList(grades_str.split(","));

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
