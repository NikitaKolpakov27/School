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
import java.util.Map;
import java.util.Scanner;

public class LoginServlet extends HttpServlet {
    private static UserManager userManager;

    public void init() {
        userManager = Main.userManager;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.getWriter().append("Served at: ").append(request.getContextPath());

        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        boolean res = false;

        try {
            res = loginUser(login, password, out);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (res) {
            out.println("You have sign in successfully!");
            //response.sendRedirect("choice.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("choice.jsp");
            dispatcher.forward(request, response);
        }
    }

    public boolean loginUser(String loginReq, String passwordReq, PrintWriter pw) throws IOException, SQLException {
        if (!userManager.getAccounts().containsKey(loginReq) || !userManager.getAccounts().containsValue(passwordReq)) {
            pw.println("Incorrect login or password!");
            return false;
        } else if (!userManager.getAccounts().get(loginReq).equals(passwordReq)) {
            pw.println("Incorrect login or password!");
            return false;
        } else {
            return true;
        }


    }
}
