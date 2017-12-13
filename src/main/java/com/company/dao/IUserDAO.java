package com.company.dao;

import com.company.entity.Post;
import com.company.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Oksa on 24.11.2017.
 */
public interface IUserDAO {
    User findByID(long id) throws SQLException;

    void insertUser(User user) throws SQLException;

    void deleteUserById(Long id) throws SQLException;

    List<User> findAllUsers() throws SQLException;

    void updateUser(User user) throws SQLException;

    boolean isUserEditor(User user, Post post) throws SQLException;

    void makeUserEditor(User user, Post post) throws SQLException;

    List<User> findUsersByPost(Post post) throws SQLException;

    User findByLogin(String login) throws SQLException;
}
