package com.company.servlets;

import com.company.Main;
import com.company.service.StudentManager;
import com.company.service.TestConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GivingGradeServlet extends HttpServlet {
    private static StudentManager studentManager;

    public void init() {
        studentManager = Main.studentManager;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.getWriter().append("Served at: ").append(request.getContextPath());

        RequestDispatcher dispatcher = request.getRequestDispatcher("addStud.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        PrintWriter out = response.getWriter();

        String stud = request.getParameter("stud");
        String subj = request.getParameter("subj");
        String grade = request.getParameter("grade");

        String testStudID = stud.split("[(]")[1].split(",")[1].split("[)]")[0].trim();
        int studID = Integer.parseInt(testStudID);
        String finalSubj = subj.split("[(]")[1].split("[)]")[0];

        try {
            studentManager.addGradeBySubject(
                    Short.parseShort(grade),
                    Integer.parseInt(finalSubj),
                    studID
            );

            TestConnection.connection.setAutoCommit(false);
            ResultSet resultSet = TestConnection.statement.executeQuery("Select grades_list from test_school.grades where " +
                    "studID = " + studID + " and subjID = " + Integer.parseInt(finalSubj));
            String old_str = "";
            while (resultSet.next()) {
                old_str = resultSet.getString(1);
            }

            System.out.println("old_str: " + old_str);

            String new_str = old_str + "," + grade;
            System.out.println("new_str: " + new_str);

            String sql = "UPDATE test_school.grades SET grades_list = ? WHERE " +
                    "studID = ? and subjID = ?";
            PreparedStatement statement = TestConnection.connection.prepareStatement(sql);

            statement.setString(1, new_str);
            statement.setString(2, String.valueOf(studID));
            statement.setString(3, finalSubj);
            statement.executeUpdate();

            out.println("Grade has been given!");
            //response.sendRedirect(request.getContextPath() + "/choice");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
