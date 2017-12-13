package com.company.service;

import com.company.entity.Category;
import com.company.entity.Post;
import com.company.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Oksa on 27.11.2017.
 */
public interface IPostService {

    Post findByID(long id) throws SQLException;

    long insertPost(Post post) throws SQLException;

    void deletePostById(Long id) throws SQLException;

    List<Post> findAllPosts() throws SQLException;

    void updatePost(Post post) throws SQLException;

    List<Post> findPostsByUser(User user) throws SQLException;

    void addCategoryToPost(Post post, Category category) throws SQLException;

    List<Post> findPostsByCategories(ArrayList<Category> categories) throws SQLException;

    List<Post> findAllPublishedPosts() throws SQLException;

    List<Post> findNotUsersPost(User user) throws SQLException;

    List<Post> findPublishedPostsByUser(User user) throws SQLException;

    List<Post> findNotPublishedPostByUser(User user) throws SQLException;

    List<Post> searchPostsByString(String searchLine) throws SQLException;

    void setCategoryForPost(String[] categories, Post post) throws SQLException;

    void transformPostDescription(List<Post> post) throws SQLException;

    public Map<Integer, List<Post>> getPostsByParameters(HttpServletRequest request, int numberOfPostsPerPage) throws SQLException;

    public Map<Integer, List<Post>> getMyPostsByParameters(HttpServletRequest request, int numberOfPostsPerPage) throws SQLException;
}
