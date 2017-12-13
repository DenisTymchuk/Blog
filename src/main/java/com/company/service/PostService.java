package com.company.service;

import com.company.dao.CategoryDAO;
import com.company.dao.PostDAO;
import com.company.entity.Category;
import com.company.entity.Post;
import com.company.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.Array;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Oksa on 27.11.2017.
 */
public class PostService implements IPostService {
    private PostDAO postDAO;
    private CategoryDAO categoryDAO;

    public PostService() {
        this.postDAO = new PostDAO();
        this.categoryDAO = new CategoryDAO();
    }


    @Override
    public Post findByID(long id) throws SQLException {
        return postDAO.findByID(id);
    }

    @Override
    public long insertPost(Post post) throws SQLException {
        return postDAO.insertPost(post);
    }

    @Override
    public void deletePostById(Long id) throws SQLException {
        postDAO.deletePostById(id);
    }

    @Override
    public List<Post> findAllPosts() throws SQLException {
        return postDAO.findAllPosts();
    }

    @Override
    public void updatePost(Post post) throws SQLException {
        postDAO.updatePost(post);
    }

    @Override
    public List<Post> findPostsByUser(User user) throws SQLException {
        return postDAO.findPostsByUser(user);
    }

    @Override
    public void addCategoryToPost(Post post, Category category) throws SQLException {
        postDAO.addCategoryToPost(post, category);
    }

    @Override
    public List<Post> findPostsByCategories(ArrayList<Category> categories) throws SQLException {
        return postDAO.findPostsByCategories(categories);
    }

    @Override
    public List<Post> findAllPublishedPosts() throws SQLException {
        return postDAO.findAllPublishedPosts();
    }

    public List<Post> findNotUsersPost(User user) throws SQLException {
        return postDAO.findNotUsersPost(user);
    }

    @Override
    public List<Post> findPublishedPostsByUser(User user) throws SQLException {
        return postDAO.findPublishedPostsByUser(user);
    }

    @Override
    public List<Post> findNotPublishedPostByUser(User user) throws SQLException {
        return postDAO.findNotPublishedPostByUser(user);
    }

    @Override
    public List<Post> searchPostsByString(String searchLine) throws SQLException {
        List<String> lines = Arrays.asList(searchLine.split("\\s"));
        return postDAO.searchPostsByListOfStrings(lines);
    }

    @Override
    public  void setCategoryForPost(String[] categories, Post post) throws SQLException {
        if (categories != null) {
            for (int i = 0; i < categories.length; i++) {
                Category category = new Category();
                category.setName(categories[i].substring(0, categories[i].length()));
                category.setId(categoryDAO.findByName(category.getName()).getId());
                categoryDAO.insertCategoryByPost(post, category);
            }
        }
    }

    @Override
    public void transformPostDescription(List<Post> posts) throws SQLException {
        for (Post post : posts) {
            post.setDescription(post.getDescription().replace("\n", "<br>"));
        }
    }

    @Override
    public Map<Integer, List<Post>> getPostsByParameters(HttpServletRequest request, int numberOfPostsPerPage) throws SQLException {
        int numberOfPages = 1;
        String searchLine = request.getParameter("searchLine");
        if (searchLine == null) {
            searchLine = "";
        } else {
            request.setAttribute("searchLine", searchLine);
        }
        String sortLine = request.getParameter("sortBy");
        if (sortLine != null) {
            request.setAttribute("sortBy", sortLine);
        }

        int currentPage;
        if (request.getParameter("page") == null) {
            currentPage = 1;
            request.setAttribute("currentPage", currentPage);
        } else {
            currentPage = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("currentPage", currentPage);
        }
        return postDAO.getPostsByParameters(searchLine, sortLine, currentPage,
                (User)request.getSession().getAttribute("loginedUser"), numberOfPostsPerPage);
    }

    @Override
    public Map<Integer, List<Post>> getMyPostsByParameters(HttpServletRequest request, int numberOfPostsPerPage) throws SQLException {
        int numberOfPages = 1;
        String searchLine = request.getParameter("searchLine");
        if (searchLine == null) {
            searchLine = "";
        } else {
            request.setAttribute("searchLine", searchLine);
        }
        String sortLine = request.getParameter("sortBy");
        if (sortLine != null) {
            request.setAttribute("sortBy", sortLine);
        }

        int currentPage;
        if (request.getParameter("page") == null) {
            currentPage = 1;
            request.setAttribute("currentPage", currentPage);
        } else {
            currentPage = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("currentPage", currentPage);
        }
        return postDAO.getMyPostsByParameters(searchLine, sortLine, currentPage,
                (User)request.getSession().getAttribute("loginedUser"), numberOfPostsPerPage);
    }
}
