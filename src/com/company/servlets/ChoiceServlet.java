package com.company.servlets;

import com.company.Main;
import com.company.model.Klass;
import com.company.model.Student;
import com.company.model.Subject;
import com.company.model.User;
import com.company.service.KlassManager;
import com.company.service.StudentManager;
import com.company.service.SubjectManager;
import com.company.service.UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChoiceServlet extends HttpServlet {
    private static StudentManager studentManager;
    private static KlassManager klassManager;
    private static SubjectManager subjectManager;
    private static UserManager userManager;

    public void init() {
        studentManager = Main.studentManager;
        klassManager = Main.klassManager;
        subjectManager = Main.subjectManager;
        userManager = Main.userManager;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.getWriter().append("Served at: ").append(request.getContextPath());

        RequestDispatcher dispatcher = request.getRequestDispatcher("choice.jsp");
        dispatcher.forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        PrintWriter out = response.getWriter();

        String get = request.getParameter("button");
//        System.out.println(get);

        switch (get) {
            case ("Display students"):
                out.println("Students: ");
                for (Map.Entry<Integer, Student> pair : studentManager.students.entrySet()) {
                    out.println(pair.getKey() + " = " + pair.getValue().toString());
                }
                break;
            case ("Display class schedule"):  //Расписание всех классов
                for (Map.Entry<Integer, Klass> pair : klassManager.klasses.entrySet()) {
                    int klassID = pair.getValue().klassId;

                    Map<Integer, List<Timestamp>> schedule = Main.klassManager.getSchedule(klassID);

                    out.println("Schedule for " + klassManager.klasses.get(klassID).name + ":");
                    out.println("---------------------");
                    DateFormat df = new SimpleDateFormat("dd MMM yyyy");

                    for (Map.Entry<Integer, List<Timestamp>> pair1 : schedule.entrySet()) {

                        out.println("shedule");

                        //hm...
                        if (schedule.isEmpty()) {
                            out.println("Schedule for this class is not created yet.");
                            break;
                        }

                        List<String> dateList = new ArrayList<>();
                        for (int i = 0; i < pair1.getValue().size(); i++) {
                            dateList.add(df.format(pair1.getValue().get(i).getTime()));
                        }
                        out.println(Main.subjectManager.subjects.get(pair1.getKey()) + " = " + dateList);
                    }
                    out.println("---------------------");
                    out.println();
                }
                break;
            case ("Display students grades"):
                for (Map.Entry<Integer, Student> pair: studentManager.students.entrySet()) {

                    String str = "ОЦЕНКИ: ";
                    if (pair.getValue().grades.entrySet().size() == 0) {
                        str += "Пока нет оценок.";
                    }

                    for (Map.Entry<Integer, List<Short>> par : pair.getValue().grades.entrySet()) {
                        str += subjectManager.subjects.get(par.getKey()) + " = " + par.getValue();
                        str += " ";
                    }

                    out.println("ID = " + pair.getKey() + "; " + pair.getValue().toString() + "; " +
                            str);
                }
                break;
            case ("Take a grade to the student"):
                out.println("IN process!");
                break;
            case ("Add student"):
                out.println("IN process!");
                break;
            case ("Remove student"):
                out.println("IN process!");
                break;
            case ("Display subjects"):
                out.println("Subjects:");
                out.println("-----------------");
                for (Map.Entry<Integer, Subject> pair: subjectManager.subjects.entrySet()) {
                    out.println("ID = " + pair.getKey() + "; " + pair.getValue().toString() + ";");
                }
                out.println("-----------------");
                out.println();
                break;
            default:
                out.println("work in progress");
                break;


        }

    }
}
