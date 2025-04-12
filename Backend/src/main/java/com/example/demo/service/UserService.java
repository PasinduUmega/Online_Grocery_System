package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.util.UserFileUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private static final String FILE_PATH = "data/users.txt";

    public List<User> getAllUsers() {
        return UserFileUtils.readUsersFromFile(FILE_PATH);
    }

    public void addUser(User user) {
        List<User> users = getAllUsers();

        // Generate new user ID
        String newId = "U" + UUID.randomUUID().toString().substring(0, 8);  // You can use UUID or timestamp too
        user.setId(newId);  // make sure User.java has setId()

        users.add(user);
        UserFileUtils.writeUsersToFile(FILE_PATH, users);
    }

    public void updateUser(String id, User updateUser) {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            User existingUser = users.get(i);
            if (existingUser.getId().equals(id)) {

                // Only update if value is not null
                if (updateUser.getName() != null)
                    existingUser.setName(updateUser.getName());

                if (updateUser.getEmail() != null)
                    existingUser.setEmail(updateUser.getEmail());

                if (updateUser.getPassword() != null)
                    existingUser.setPassword(updateUser.getPassword());

                break;
            }
        }
        UserFileUtils.writeUsersToFile(FILE_PATH, users);
    }

    public void deleteUser(String id) {
        List<User> users = getAllUsers();
        users.removeIf(user -> user.getId().equals(id));
        UserFileUtils.writeUsersToFile(FILE_PATH, users);
    }
}
