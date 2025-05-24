package com.example.demo.util;


import com.example.demo.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFileUtils {
    public static List<User> readUsersFromFile(String filePath) {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    User user = new User(parts[0], parts[1], parts[2], parts[3]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void writeUsersToFile(String filePath, List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                writer.write(String.join(",",
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPassword()
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
