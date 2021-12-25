package com.company.servlets;

import com.company.Main;
import com.company.model.Student;
import com.company.service.KlassManager;
import com.company.service.StudentManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class RemoveStudentServlet extends HttpServlet {
    private static StudentManager studentManager;
    private static KlassManager klassManager;

    public void init() {
        studentManager = Main.studentManager;
        klassManager = Main.klassManager;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.getWriter().append("Served at: ").append(request.getContextPath());

        RequestDispatcher dispatcher = request.getRequestDispatcher("delStud.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        PrintWriter out = response.getWriter();

        String stud = request.getParameter("stud");
        System.out.println(stud);

        String testKlassID = stud.split("[(]")[1].split(",")[0];
        String testStudID = stud.split("[(]")[1].split(",")[1].split("[)]")[0].trim();

        int klassId = Integer.parseInt(testKlassID);
        int studID = Integer.parseInt(testStudID);
        System.out.println(klassId + "=" + studID);

        Student removingStudent = studentManager.students.get(studID);
        System.out.println("Removing student " + removingStudent);
        String name = removingStudent.toString();

        try {
            klassManager.removeStudent(removingStudent, removingStudent.klassId);
            out.println("Student " + name + " has been removed!");
            //response.sendRedirect("/${pageContext.request.contextPath}/choice");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
