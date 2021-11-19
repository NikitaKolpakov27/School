package com.company.service;

import com.company.model.Klass;
import com.company.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserManager {
    public final Map<Integer, User> users;
    public final Map<String, String> accounts;

    public HashMap<Integer, User> getUsers() {
        return (HashMap<Integer, User>) users;
    }

    public HashMap<String, String> getAccounts() {
        return (HashMap<String, String>) accounts;
    }

    public UserManager() throws SQLException {
        this.accounts = new HashMap<>();
        ResultSet resultSet = TestConnection.statement.executeQuery("Select * from school.users");
        List<User> usersList = new ArrayList<>();
        while (resultSet.next()) {

            usersList.add(
                    new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3))
            );

            this.accounts.put(resultSet.getString(2), resultSet.getString(3));
        }

        this.users = usersList.stream().collect(Collectors.toMap(
                user -> user.userID,
                Function.identity()
        ));
    }

    public void changeLogin(int userID, String new_login) throws SQLException {
        String oldLogin = this.users.get(userID).login;

        accounts.remove(oldLogin);
        this.users.get(userID).login = new_login;
        TestConnection.statement.executeUpdate("Update school.users Set login = " + new_login);

        accounts.put(new_login, this.users.get(userID).password);
    }

    public void changePassword(int userID, String new_password) throws SQLException {
        String oldPassword = this.users.get(userID).password;

        this.users.get(userID).password = new_password;
        TestConnection.statement.executeUpdate("Update school.users Set password = " + new_password);

        accounts.replace(this.users.get(userID).login, new_password);
    }

    public void removeUser(User user) throws SQLException {
        accounts.remove(user.login);

        this.users.remove(user.userID);
        TestConnection.statement.executeUpdate("Delete from school.users where studID = " + user.userID);
    }

    private void addUser_inner(User user) {
        accounts.put(user.login, user.password);
        this.users.put(user.userID, user);
    }


    public void addUser(User user) throws SQLException {
        accounts.put(user.login, user.password);

        int newId = this.users.size();
        this.users.put(newId, user);

        String sql = "INSERT INTO school.users (userID, login, password) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = TestConnection.connection.prepareStatement(sql);
        preparedStatement.setInt(1, user.userID);
        preparedStatement.setString(2, user.login);
        preparedStatement.setString(3, user.password);
        preparedStatement.executeUpdate();
    }
}
