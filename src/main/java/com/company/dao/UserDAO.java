package com.company.dao;

import com.company.entity.Post;
import com.company.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oksa on 24.11.2017.
 */
public class UserDAO implements IUserDAO {
    @Override
    public User findByID(long id) throws SQLException {
        User user = null;
        String sql = "SELECT  * FROM users WHERE id = ?";
        PreparedStatement preparedStatement;
        ResultSet rs;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);
        rs = preparedStatement.executeQuery();


        while (rs.next()) {
            user = new User();
            user.setId(id);
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }
        preparedStatement.close();
        rs.close();
        return user;
    }

    @Override
    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, password) VALUES(?,?)";
        PreparedStatement preparedStatement;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteUserById(Long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement preparedStatement;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public List<User> findAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        List<User> users = new LinkedList<>();
        Statement statement;
        ResultSet rs;

        statement = DBConnection.getConnection().createStatement();
        rs = statement.executeQuery(sql);

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            users.add(user);
        }
        rs.close();
        statement.close();
        return users;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name = '?'/, password = '?' WHERE id = ?";
        PreparedStatement preparedStatement;
        if (this.findByID(user.getId()) == null ) {
            this.insertUser(user);
        } else {
            preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getId());
            preparedStatement.execute();
            preparedStatement.close();
        }
    }

    @Override
    public boolean isUserEditor(User user, Post post) throws SQLException {
        String sql = "SELECT FROM users_posts WHERE user_id =? AND post_id =?";
        PreparedStatement preparedStatement;
        ResultSet rs = null;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, user.getId());
        preparedStatement.setLong(2, post.getId());
        rs = preparedStatement.executeQuery();
        if (rs.next()) {
            rs.close();
            preparedStatement.close();
            return true;
        }
        rs.close();
        preparedStatement.close();
        return false;
    }

    @Override
    public void makeUserEditor(User user, Post post) throws SQLException {
        String sql = "INSERT INTO users_posts(user_id, post_id) VALUES(?,?)";
        PreparedStatement preparedStatement;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, user.getId());
        preparedStatement.setLong(2, post.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public List<User> findUsersByPost(Post post) throws SQLException {
        List<User> result = new LinkedList<>();
        String sql = "SELECT FROM users_posts WHERE post_id =?";
        PreparedStatement preparedStatement;
        ResultSet rs;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, post.getId());
        rs = preparedStatement.executeQuery();

        while (rs.next()) {
            User user = findByID(rs.getLong("users_id"));
            result.add(user);
        }
        rs.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public User findByLogin(String login) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM users WHERE name = ? ";
        PreparedStatement preparedStatement;
        ResultSet rs;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, login);
        rs = preparedStatement.executeQuery();

        if (rs.next()) {
            user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }
        preparedStatement.close();
        rs.close();
        return user;
    }
}
