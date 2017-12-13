package com.company.dao;

import com.company.entity.Category;
import com.company.entity.Post;
import com.company.entity.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Oksa on 24.11.2017.
 */
public interface IPostDAO {

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

    List<Post> searchPostsByListOfStrings(List<String> strings) throws SQLException;

    public Map<Integer, List<Post>> getPostsByParameters(String searchLine, String sortLine, int currentPage,
                                                         User loginedUser, int numberOfPostsPerPage) throws SQLException;

    public Map<Integer, List<Post>> getMyPostsByParameters(String searchLine, String sortLine, int currentPage,
                                                         User loginedUser, int numberOfPostsPerPage) throws SQLException;
}
