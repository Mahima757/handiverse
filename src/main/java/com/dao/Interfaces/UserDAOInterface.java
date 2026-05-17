package com.dao.Interfaces;

import java.util.List;
import com.model.User;

public interface UserDAOInterface {

    boolean addUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    boolean updateUser(User user);

    boolean deleteUser(int id);

	User getUser(String userName);
}
