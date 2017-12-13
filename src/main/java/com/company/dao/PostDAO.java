package com.company.dao;

import com.company.entity.Category;
import com.company.entity.Post;
import com.company.entity.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Oksa on 24.11.2017.
 */
public class PostDAO implements IPostDAO {
    CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    public Post findByID(long id) throws SQLException {
        Post post = null;
        String sql = "SELECT * FROM post WHERE id = ? ";
        PreparedStatement preparedStatement;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            post = new Post();
            post.setId(id);
            post.setTittle(rs.getString("tittle"));
            post.setDescription(rs.getString("description"));
            UserDAO userDAO = new UserDAO();
            post.setUserCreator(userDAO.findByID(rs.getLong("user_creator_id")));
            post.setDateOfThePost(String.valueOf(rs.getTimestamp("date_of_the_post")));
            post.setPublished(rs.getBoolean("published"));
            post.setImageLink(rs.getString("image_link"));
            post.setCategories(categoryDAO.findCategoriesByPost(post));
        }
        preparedStatement.close();
        rs.close();
        return post;
    }

    @Override
    public long insertPost(Post post) throws SQLException {
        String sql = "INSERT INTO post (user_creator_id, tittle, description, date_of_the_post, published, image_link) " +
                "VALUES(?,?,?,?,?,?)";
        PreparedStatement preparedStatement;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sDate = sdf.format(cal.getTime());
        post.setDateOfThePost(sDate);

        Timestamp timestamp = Timestamp.valueOf(sDate);
        preparedStatement = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, post.getUserCreator().getId());
        preparedStatement.setString(2, post.getTittle());
        preparedStatement.setString(3, post.getDescription());
        preparedStatement.setTimestamp(4, timestamp);
        preparedStatement.setBoolean(5, post.isPublished());
        preparedStatement.setString(6, post.getImageLink());
        preparedStatement.execute();
        ResultSet rs = preparedStatement.getGeneratedKeys();

        if (rs.next()) {
            return rs.getLong(1);
        }
        preparedStatement.close();
        return -1;
    }

    @Override
    public void deletePostById(Long id) throws SQLException {
        String sql = "DELETE FROM post WHERE id = ?";
        String sql1 = "DELETE FROM post_category WHERE post_id=?";
        PreparedStatement preparedStatement;
        PreparedStatement preparedStatement1;

        preparedStatement1 = DBConnection.getConnection().prepareStatement(sql1);
        preparedStatement1.setLong(1,id);
        preparedStatement1.executeUpdate();
        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public List<Post> findAllPosts() throws SQLException {
        String sql = "SELECT * from post ORDER BY date_of_the_post DESC";
        List<Post> result = new LinkedList<>();

        Statement statement;
        ResultSet rs;

        statement = DBConnection.getConnection().createStatement();
        rs = statement.executeQuery(sql);
        while (rs.next()) {

            Post post = findByID(rs.getLong("id"));
            result.add(post);
        }
        statement.close();
        rs.close();
        return result;
    }

    @Override
    public void updatePost(Post post) throws SQLException {
        String sql = "UPDATE post SET tittle=?, description=?, date_of_the_post=?, published=?, image_link=? WHERE id = ?";
        PreparedStatement preparedStatement;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sDate = sdf.format(cal.getTime());
        post.setDateOfThePost(sDate);

        Timestamp timestamp = Timestamp.valueOf(sDate);
        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, post.getTittle());
        preparedStatement.setString(2, post.getDescription());
        preparedStatement.setTimestamp(3, timestamp);
        preparedStatement.setBoolean(4, post.isPublished());
        preparedStatement.setString(5, post.getImageLink());
        preparedStatement.setLong(6, post.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public List<Post> findPostsByUser(User user) throws SQLException {
        List<Post> result = new LinkedList<>();
        String sql = "SELECT * FROM post WHERE user_creator_id = ? ORDER BY date_of_the_post DESC";
        PreparedStatement preparedStatement;
        ResultSet rs;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, user.getId());
        rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Post post = findByID(rs.getLong("id"));
            post.setCategories(categoryDAO.findCategoriesByPost(post));
            result.add(post);
        }
        rs.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public void addCategoryToPost(Post post, Category category) throws SQLException {
        String sql = "INSERT INTO post_category(post_id, category_id) VALUES(?,?)";
        PreparedStatement preparedStatement;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, post.getId());
        preparedStatement.setLong(2, category.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public List<Post> findPostsByCategories(ArrayList<Category> categories) throws SQLException {
        List<Post> posts = new LinkedList<>();
        String sql = "SELECT FROM post_category WHERE category_id = ?";
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = null;

        for (Category category : categories) {
            preparedStatement.setLong(1, category.getId());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Post post = findByID(rs.getLong("post_id"));
                posts.add(post);
            }
        }
        rs.close();
        preparedStatement.close();
        return posts;
    }

    @Override
    public List<Post> findAllPublishedPosts() throws SQLException {
        String sql = "SELECT * from post WHERE published = true ORDER BY date_of_the_post DESC";
        List<Post> result = new LinkedList<>();

        Statement statement;
        ResultSet rs;

        statement = DBConnection.getConnection().createStatement();
        rs = statement.executeQuery(sql);
        while (rs.next()) {
            Post post = findByID(rs.getLong("id"));
            post.setCategories(categoryDAO.findCategoriesByPost(post));
            result.add(post);
        }
        statement.close();
        rs.close();
        return result;
    }

    @Override
    public List<Post> findNotUsersPost(User user) throws SQLException {
        List<Post> result = new LinkedList<>();
        String sql = "SELECT * FROM post WHERE user_creator_id != ? AND published = true ORDER BY date_of_the_post DESC";
        PreparedStatement preparedStatement;
        ResultSet rs;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, user.getId());
        rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Post post = findByID(rs.getLong("id"));
            result.add(post);
        }
        rs.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public List<Post> findPublishedPostsByUser(User user) throws SQLException {
        List<Post> result = new LinkedList<>();
        String sql = "SELECT * FROM post WHERE user_creator_id = ? AND published = TRUE ORDER BY date_of_the_post DESC";
        PreparedStatement preparedStatement;
        ResultSet rs;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, user.getId());
        rs = preparedStatement.executeQuery();


        while (rs.next()) {
            Post post = findByID(rs.getLong("id"));
            post.setCategories(categoryDAO.findCategoriesByPost(post));
            result.add(post);
        }
        rs.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public List<Post> findNotPublishedPostByUser(User user) throws SQLException {
        List<Post> result = new LinkedList<>();
        String sql = "SELECT * FROM post WHERE user_creator_id = ? AND published = FALSE ORDER BY date_of_the_post DESC";
        PreparedStatement preparedStatement;
        ResultSet rs;

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, user.getId());
        rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Post post = findByID(rs.getLong("id"));
            post.setCategories(categoryDAO.findCategoriesByPost(post));
            result.add(post);
        }
        rs.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public List<Post> searchPostsByListOfStrings(List<String> strings) throws SQLException {
        List<Post> result = new LinkedList<>();
        String sql = "SELECT id FROM post WHERE LOWER(tittle) LIKE LOWER(?) OR LOWER(description) LIKE LOWER(?) " +
                "ORDER BY date_of_the_post DESC";
        PreparedStatement preparedStatement;
        ResultSet rs;
        Set<Long> idPosts = new HashSet<>();

        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        for (String line : strings) {
            preparedStatement.setString(1, "%" + line + "%");
            preparedStatement.setString(2, "%" + line + "%");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                idPosts.add(rs.getLong("id"));
            }
        }

        for (Long id : idPosts) {
            Post post;
            post = findByID(id);
            post.setCategories(categoryDAO.findCategoriesByPost(post));
            result.add(post);
        }
        return result;
    }

    @Override
    public Map<Integer, List<Post>> getPostsByParameters(String searchLine, String sortLine, int currentPage,
                                                         User loginedUser, int numberOfPostsPerPage) throws SQLException {
        String sql;
        PreparedStatement preparedStatement;
        ResultSet rs = null;
        Map<Integer, List<Post>> result = new HashMap<>();
        List<Post> listResult = new LinkedList<>();
        Integer numberOfPosts = 0;

        if (sortLine == null || sortLine.equals("") || sortLine.equals("default")) {
            sql = "SELECT * FROM post WHERE (LOWER(tittle) LIKE LOWER(?) OR LOWER(description) LIKE LOWER(?)) " +
                    "AND published=true ORDER BY date_of_the_post DESC LIMIT ? OFFSET ?";
            preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, "%" + searchLine + "%");
            preparedStatement.setString(2, "%" + searchLine + "%");
            preparedStatement.setInt(3, numberOfPostsPerPage);
            preparedStatement.setInt(4, (currentPage * numberOfPostsPerPage) - numberOfPostsPerPage);
            rs = preparedStatement.executeQuery();
        } else if (sortLine.equals("firstMy")) {
            sql = "SELECT *, user_creator_id=? AS col FROM post WHERE (LOWER(tittle) LIKE LOWER(?) OR LOWER(description) " +
                    "LIKE LOWER(?)) AND published=true ORDER BY col DESC, date_of_the_post DESC LIMIT ? OFFSET ?";
            preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, loginedUser.getId());
            preparedStatement.setString(2, "%" + searchLine + "%");
            preparedStatement.setString(3, "%" + searchLine + "%");
            preparedStatement.setInt(4, numberOfPostsPerPage);
            preparedStatement.setInt(5, (currentPage * numberOfPostsPerPage) - numberOfPostsPerPage);
            rs = preparedStatement.executeQuery();
        } else if (sortLine.equals("lastMy")) {
            sql = "SELECT *, user_creator_id=? AS col FROM post WHERE (LOWER(tittle) LIKE LOWER(?) OR LOWER(description) " +
                    "LIKE LOWER(?)) AND published=true ORDER BY col ASC, date_of_the_post DESC LIMIT ? OFFSET ?";
            preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, loginedUser.getId());
            preparedStatement.setString(2, "%" + searchLine + "%");
            preparedStatement.setString(3, "%" + searchLine + "%");
            preparedStatement.setInt(4, numberOfPostsPerPage);
            preparedStatement.setInt(5, (currentPage * numberOfPostsPerPage) - numberOfPostsPerPage);
            rs = preparedStatement.executeQuery();
        }

        while (rs.next()) {
            Post post = new Post();
            post = findByID(rs.getLong("id"));
            post.setCategories(categoryDAO.findCategoriesByPost(post));
            listResult.add(post);
        }
        
        sql = "SELECT COUNT(*) FROM post WHERE (LOWER(tittle) LIKE LOWER(?) OR LOWER(description) " +
                "LIKE LOWER(?)) AND published=true";
        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, "%" + searchLine + "%");
        preparedStatement.setString(2, "%" + searchLine + "%");
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            numberOfPosts = rs.getInt(1);
        }

        result.put(numberOfPosts, listResult);
        return result;
    }

    @Override
    public Map<Integer, List<Post>> getMyPostsByParameters(String searchLine, String sortLine, int currentPage,
                                                           User loginedUser, int numberOfPostsPerPage) throws SQLException {
        String sql;
        PreparedStatement preparedStatement;
        ResultSet rs = null;
        Map<Integer, List<Post>> result = new HashMap<>();
        List<Post> listResult = new LinkedList<>();
        Integer numberOfPosts = 0;

        if (sortLine == null || sortLine.equals("") || sortLine.equals("default")) {
            sql = "SELECT * FROM post WHERE (LOWER(tittle) LIKE LOWER(?) OR LOWER(description) LIKE LOWER(?)) " +
                    "AND user_creator_id=? ORDER BY date_of_the_post DESC LIMIT ? OFFSET ?";
            preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, "%" + searchLine + "%");
            preparedStatement.setString(2, "%" + searchLine + "%");
            preparedStatement.setLong(3,loginedUser.getId());
            preparedStatement.setInt(4, numberOfPostsPerPage);
            preparedStatement.setInt(5, (currentPage * numberOfPostsPerPage) - numberOfPostsPerPage);
            rs = preparedStatement.executeQuery();
        } else if (sortLine.equals("firstPublished")) {
            sql = "SELECT * FROM post WHERE (LOWER(tittle) LIKE LOWER(?) OR LOWER(description) " +
                    "LIKE LOWER(?)) AND user_creator_id=? ORDER BY published DESC LIMIT ? OFFSET ?";
            preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, "%" + searchLine + "%");
            preparedStatement.setString(2, "%" + searchLine + "%");
            preparedStatement.setLong(3,loginedUser.getId());
            preparedStatement.setInt(4, numberOfPostsPerPage);
            preparedStatement.setInt(5, (currentPage * numberOfPostsPerPage) - numberOfPostsPerPage);
            rs = preparedStatement.executeQuery();
        } else if (sortLine.equals("firstNotPublished")) {
            sql = "SELECT * FROM post WHERE (LOWER(tittle) LIKE LOWER(?) OR LOWER(description) " +
                    "LIKE LOWER(?)) AND user_creator_id=? ORDER BY published LIMIT ? OFFSET ?";
            preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, "%" + searchLine + "%");
            preparedStatement.setString(2, "%" + searchLine + "%");
            preparedStatement.setLong(3,loginedUser.getId());
            preparedStatement.setInt(4, numberOfPostsPerPage);
            preparedStatement.setInt(5, (currentPage * numberOfPostsPerPage) - numberOfPostsPerPage);
            rs = preparedStatement.executeQuery();
        }

        while (rs.next()) {
            Post post = new Post();
            post = findByID(rs.getLong("id"));
            post.setCategories(categoryDAO.findCategoriesByPost(post));
            listResult.add(post);
        }

        sql = "SELECT COUNT(*) FROM post WHERE (LOWER(tittle) LIKE LOWER(?) OR LOWER(description) " +
                "LIKE LOWER(?)) AND user_creator_id=?";
        preparedStatement = DBConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, "%" + searchLine + "%");
        preparedStatement.setString(2, "%" + searchLine + "%");
        preparedStatement.setLong(3, loginedUser.getId());
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            numberOfPosts = rs.getInt(1);
        }

        result.put(numberOfPosts, listResult);
        return result;
    }
}

