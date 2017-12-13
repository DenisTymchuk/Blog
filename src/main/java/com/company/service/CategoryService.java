package com.company.service;

import com.company.dao.CategoryDAO;
import com.company.entity.Category;
import com.company.entity.Post;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oksa on 27.11.2017.
 */
public class CategoryService implements ICategoryService {
    private CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    @Override
    public Category findByID(long id) throws SQLException {
        return categoryDAO.findByID(id);
    }

    @Override
    public void insertCategory(Category category) throws SQLException {
        categoryDAO.insertCategory(category);
    }

    @Override
    public void deleteCategoryById(Long id) throws SQLException {
        categoryDAO.deleteCategoryById(id);
    }

    @Override
    public List<Category> findAllCategories() throws SQLException {
        return categoryDAO.findAllCategories();
    }

    @Override
    public void deleteAllCategorisByPost(Post post) throws SQLException {
        categoryDAO.deleteAllCategoriesByPost(post);
    }

    @Override
    public void insertCategoryByLine(String categoryLine, Post post) throws SQLException {
        List<String> categories = Arrays.asList(categoryLine.split(",\\s"));
        for (String eachCategoryName : categories) {
            Category category = new Category();
            category.setName(eachCategoryName);
            categoryDAO.insertCategory(category);
            category.setId(categoryDAO.findByName(eachCategoryName).getId());
            categoryDAO.insertCategoryByPost(post, category);
        }
    }

    @Override
    public void updateCategoryByLine(String categoryLine, Post post) throws SQLException {
        List<String> categories = Arrays.asList(categoryLine.split(",\\s"));
        for (String eachCategoryName : categories) {
            Category category = new Category();
            if (categoryDAO.findByName(eachCategoryName) == null) {
                category.setName(eachCategoryName);
                categoryDAO.insertCategory(category);
                category.setId(categoryDAO.findByName(eachCategoryName).getId());
            }
            categoryDAO.insertCategoryByPost(post, category);
        }
    }
}
