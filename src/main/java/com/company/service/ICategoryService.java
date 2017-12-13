package com.company.service;

import com.company.entity.Category;
import com.company.entity.Post;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Oksa on 27.11.2017.
 */
public interface ICategoryService {
    Category findByID(long id) throws SQLException;

    void insertCategory(Category category) throws SQLException;

    void deleteCategoryById(Long id) throws SQLException;

    List<Category> findAllCategories() throws SQLException;

    public void deleteAllCategorisByPost(Post post) throws SQLException;

    void insertCategoryByLine(String categoryLine, Post post) throws SQLException;

    void updateCategoryByLine(String categoryLine, Post post) throws SQLException;
}
