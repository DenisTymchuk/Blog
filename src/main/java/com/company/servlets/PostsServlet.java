package com.company.servlets;

import com.company.entity.Post;
import com.company.entity.User;
import com.company.service.PostService;
import com.company.utils.MyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Oksa on 27.11.2017.
 */
@WebServlet(name = "PostsServlet", urlPatterns = "/posts")
public class PostsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numberOfPostsPerPage = 5;
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PostService postService = new PostService();
        List<Post> posts = new LinkedList<>();
        Map<Integer, List<Post>> map = new HashMap<>();
        int numberOfPages = 1;


        try {
            map = postService.getPostsByParameters(request, numberOfPostsPerPage);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Map.Entry<Integer, List<Post>> entry : map.entrySet()) {
            posts = entry.getValue();
            numberOfPages = (int)Math.ceil(entry.getKey()*1.0/numberOfPostsPerPage*1.0);
        }
        request.setAttribute("numberOfPages", numberOfPages);
        try {
            postService.transformPostDescription(posts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("posts", posts);
        request.getRequestDispatcher("/WEB-INF/views/posts.jsp").forward(request, response);
    }
}
