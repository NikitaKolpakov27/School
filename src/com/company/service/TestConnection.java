package com.company.service;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestConnection {
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "agora1948";
    public static final String URL = "jdbc:mysql://localhost:3306/mysql" +
            "?verifyServerCertificate=false"+
            "&useSSL=false"+
            "&requireSSL=false"+
            "&useLegacyDatetimeCode=false"+
            "&amp"+
            "&serverTimezone=UTC";
    public static Statement statement;
    public static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        ResultSet resultSet = statement.executeQuery("Select * from test_school.grades");
//        List<Short> grades_short = new ArrayList<>();
//        String str = "";
//        while (resultSet.next()) {
//            str += resultSet.getString(4);
//        }
//        List<String> array = Arrays.asList(str.split(","));
//        System.out.println("String list: " + array);
//
//        for (String st : array) {
//            grades_short.add(Short.parseShort(st));
//        }
//
//        System.out.println("Short list: " + grades_short);

        ResultSet resultSet = statement.executeQuery("Select * from test_school.newschedule");
        DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm");
        while (resultSet.next()) {
//            System.out.println(df.format(resultSet.getTimestamp(4)));
            Timestamp date = resultSet.getTimestamp(4);
            System.out.println(date);
        }
    }

}
