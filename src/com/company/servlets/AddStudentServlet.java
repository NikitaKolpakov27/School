package com.company.servlets;

import com.company.Main;
import com.company.model.Student;
import com.company.service.KlassManager;
import com.company.service.StudentManager;
import com.company.service.UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class AddStudentServlet extends HttpServlet {
    private static StudentManager studentManager;
    private static KlassManager klassManager;

    public void init() {
        studentManager = Main.studentManager;
        klassManager = Main.klassManager;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.getWriter().append("Served at: ").append(request.getContextPath());

        RequestDispatcher dispatcher = request.getRequestDispatcher("addStud.jsp");
        dispatcher.forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String patronymic = request.getParameter("patronymic");
        int klassID = Integer.parseInt(request.getParameter("klassID"));
        int studID = studentManager.students.size();


        Student new_stud;
        try {

            if (studentManager.students.containsKey(studID)) {
                studID += 1;
            }

            new_stud = new Student(name, surname, patronymic, klassID, studID);

            klassManager.addStudent(new_stud, klassID);
            out.println("Student has been added!");
            //не работает
            response.sendRedirect("/" + request.getContextPath() + "/choice");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
