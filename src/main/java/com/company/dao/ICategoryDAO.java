package com.company.dao;

import com.company.entity.Category;
import com.company.entity.Post;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Oksa on 24.11.2017.
 */
public interface ICategoryDAO {
    Category findByID(long id) throws SQLException;

    Category findByName(String name) throws SQLException;

    void insertCategory(Category category) throws SQLException;

    void deleteCategoryById(Long id) throws SQLException;

    List<Category> findAllCategories() throws SQLException;

    void deleteAllCategoriesByPost(Post post) throws SQLException;

    void insertCategoryByPost(Post post, Category category) throws SQLException;

    List<Category> findCategoriesByPost(Post post) throws SQLException;
}
