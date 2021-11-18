package com.company.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class School {
    public List<User> users;
    public HashMap<String, String> accounts; //Мб не нужно, но очень удобно

    public School() throws IOException {
        this.users = new ArrayList<>();
        this.accounts = new HashMap<>();
        setAccounts();
    }

    public void setAccounts() throws IOException {
        File file = new File("C:\\Users\\Колпаков Сергей\\IdeaProjects\\School\\src\\com\\company\\school_db");
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = reader.readLine();
        String totalResult = "";
        while (line != null) {
//            System.out.println(line);
            totalResult = totalResult + line + " ";
            line = reader.readLine();
        }

        if (file.length() == 0) {
            return;
        } else {
//        System.out.println("Total result: " + totalResult);
            String[] array = totalResult.split(" ");
            for (int i = 0; i < array.length; i++) {
                String[] sub_array = array[i].split("-");
                this.accounts.put(sub_array[0], sub_array[1]);
            }
        }

//        System.out.println(line);
    }
}
