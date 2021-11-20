package com.company.service;

import com.company.model.Student;
import com.company.model.Subject;
import com.company.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SubjectManager {
    public final Map<Integer, Subject> subjects;

    public SubjectManager() throws SQLException {
        ResultSet resultSet = TestConnection.statement.executeQuery("Select * from school.subjects");
        List<Subject> subjectList = new ArrayList<>();
        while (resultSet.next()) {

            subjectList.add(
                    new Subject(resultSet.getString(2),
                            Integer.parseInt(resultSet.getString(1))
            ));
        }

        this.subjects = subjectList.stream().collect(Collectors.toMap(
                subject -> subject.subjectId,
                Function.identity()
        ));
    }

    public void removeSubject(Subject subject) throws SQLException {
        this.subjects.remove(subject.subjectId);
        TestConnection.statement.executeUpdate("Delete from school.subject where subjID = " + subject.subjectId);
    }

    private void addSubject(Subject subject) throws SQLException {
        int newId = this.subjects.size();
        this.subjects.put(newId, subject);

        String sql = "INSERT INTO school.subjects (subjID, name) VALUES (?, ?)";

        PreparedStatement preparedStatement = TestConnection.connection.prepareStatement(sql);
        preparedStatement.setInt(1, subject.subjectId);
        preparedStatement.setString(2, subject.name);
        preparedStatement.executeUpdate();
    }
}
