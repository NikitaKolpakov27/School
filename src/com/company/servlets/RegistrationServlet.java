package com.company.servlets;

import com.company.Main;
import com.company.model.User;
import com.company.service.UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Scanner;

public class RegistrationServlet extends HttpServlet {
    private static UserManager userManager;

    public void init() {
        userManager = Main.userManager;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.getWriter().append("Served at: ").append(request.getContextPath());

        RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
        dispatcher.forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        for (User user : userManager.users.values()) {
            out.println(user);
        }

        try {
            registerUser(login, password, out);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("choice.jsp");
    }

    public void registerUser(String loginReq, String passwordReq, PrintWriter pw) throws IOException, SQLException {
        String login = loginReq;

        if (userManager.getAccounts().containsKey(login)) {
            pw.println("Этот логин занят");
            return;
        }

        String password = passwordReq;
        int newId = userManager.users.size();

        User new_user = new User(newId, login, password);
        userManager.addUser(new_user);
        pw.println("You have sign up successfully!");
    }
}
