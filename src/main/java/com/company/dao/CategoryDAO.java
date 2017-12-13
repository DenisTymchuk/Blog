package com.company.dao;

import com.company.entity.Category;
import com.company.entity.Post;

import java.io.Closeable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oksa on 24.11.2017.
 */
public class CategoryDAO implements ICategoryDAO {
    @Override
    public Category findByID(long id) throws SQLException {
        Category category = null;
        String sql = "SELECT FROM category WHERE id = ?";
        ResultSet rs;

        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                category = new Category();
                category.setId(id);
                category.setName(rs.getString("name"));
            }
        }
        rs.close();
        return category;
    }

    static void close(Closeable in) {
        try {
            in.close();
        } catch (Exception e) {}
    }

    @Override
    public Category findByName(String name) throws SQLException {
        Category category = new Category();
        String sql = "SELECT * FROM category WHERE name = ?";
        PreparedStatement preparedStatement;
        ResultSet rs;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, name);
        rs = preparedStatement.executeQuery();

        while (rs.next()) {
            category = new Category();
            category.setName(name);
            category.setId(rs.getLong("id"));
        }
        preparedStatement.close();
        rs.close();
        return category;
    }

    @Override
    public void insertCategory(Category category) throws SQLException {
        String sql = "INSERT INTO category(name) VALUES(?)";
        PreparedStatement preparedStatement;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, category.getName());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteCategoryById(Long id) throws SQLException {
        String sql = "DELETE FROM post WHERE id = ?";
        PreparedStatement preparedStatement;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public List<Category> findAllCategories() throws SQLException {
        String sql = "SELECT * FROM category";
        List<Category> categories = new LinkedList<>();
        Statement statement;
        ResultSet rs;

        statement = DBConnection.getConnection().createStatement();
        rs = statement.executeQuery(sql);

        while (rs.next()) {
            Category category = new Category();
            category.setId(rs.getLong("id"));
            category.setName(rs.getString("name"));
            categories.add(category);
        }
        statement.close();
        rs.close();
        return categories;
    }

    @Override
    public void deleteAllCategoriesByPost(Post post) throws SQLException {
        String sql = "DELETE FROM post_category WHERE post_id = ?";
        PreparedStatement preparedStatement;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, post.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void insertCategoryByPost(Post post, Category category) throws SQLException {
        String sql = "INSERT INTO post_category(post_id, category_id) VALUES(?,?)";
        PreparedStatement preparedStatement;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, post.getId());
        preparedStatement.setLong(2, category.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public List<Category> findCategoriesByPost(Post post) throws SQLException {
        String sql = "SELECT id, name FROM category JOIN post_category ON post_category.category_id = category.id AND post_category.post_id = ?";
        PreparedStatement preparedStatement;
        List<Category> result = new LinkedList<>();

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, post.getId());
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Category category = new Category();
            category.setId(rs.getLong("id"));
            category.setName(rs.getString("name"));
            result.add(category);
        }
        return result;
    }
}
