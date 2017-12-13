package com.company.servlets;

import com.company.entity.Post;
import com.company.service.PostService;
import com.company.service.UserService;
import com.company.utils.MyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Oksa on 30.11.2017.
 */
@WebServlet(name = "MyPostsServlet", urlPatterns = "/myPosts")
public class MyPostsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numberOfPostsPerPage = 5;
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PostService postService = new PostService();
        List<Post> posts = null;
        Map<Integer, List<Post>> map = new HashMap<>();
        int numberOfPages = 1;

        try {
            map = postService.getMyPostsByParameters(request, numberOfPostsPerPage);
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
        request.setAttribute("myposts", posts);
        request.getRequestDispatcher("/WEB-INF/views/myPosts.jsp").forward(request, response);

    }
}
