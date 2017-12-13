package com.company.service;

import com.company.dao.UserDAO;
import com.company.entity.Post;
import com.company.entity.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Oksa on 27.11.2017.
 */
public class UserService implements IUserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    @Override
    public User findByID(long id) throws SQLException {
        return userDAO.findByID(id);
    }

    @Override
    public void insertUser(User user) throws SQLException {
        userDAO.insertUser(user);
    }

    @Override
    public void deleteUserById(Long id) throws SQLException {
        userDAO.deleteUserById(id);
    }

    @Override
    public List<User> findAllUsers() throws SQLException {
        return userDAO.findAllUsers();
    }

    @Override
    public void updateUser(User user) throws SQLException {
        userDAO.updateUser(user);
    }

    @Override
    public boolean isUserEditor(User user, Post post) throws SQLException {
        return userDAO.isUserEditor(user, post);
    }

    @Override
    public void makeUserEditor(User user, Post post) throws SQLException {
        userDAO.makeUserEditor(user, post);

    }

    @Override
    public List<User> findUsersByPost(Post post) throws SQLException {
        return userDAO.findAllUsers();
    }

    @Override
    public User findByLogin(String login) throws SQLException {
        return userDAO.findByLogin(login);
    }

    @Override
    public Map<Integer, String> userLoginValidation(String userName, String password) throws SQLException {
        Map<Integer, String> errors = new HashMap<>();
        UserDAO userDAO = new UserDAO();
        User user = null;

        if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
            errors.put(1, "Empty username or password");
        } else {
            user = userDAO.findByLogin(userName);
            if (user == null || !user.getPassword().equals(password)) {
                errors.put(2, "Wrong login or password");
            }
        }
        return errors;
    }

    @Override
    public Map<Integer, String> userRegistrValidation(String userName, String password, String confirmPassword)
            throws SQLException {
        Map<Integer, String> errors = new HashMap<>();
        User user = null;
        UserDAO userDAO = new UserDAO();

        user = userDAO.findByLogin(userName);
        if (userName.equals("") || password.equals("") || confirmPassword.equals("")) {
            errors.put(1,"Empty username or password!");
        } else {
            if (user != null) {
                errors.put(2, "User with this name exists!");
            }
            if (!password.equals(confirmPassword)) {
                errors.put(3, "Password aren't the same!");
            }
        }
        return errors;
    }
}
