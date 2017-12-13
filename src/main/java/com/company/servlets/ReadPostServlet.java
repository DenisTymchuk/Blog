package com.company.servlets;

import com.company.entity.Post;
import com.company.entity.User;
import com.company.service.PostService;
import com.company.utils.MyUtils;
import org.omg.CORBA.Request;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oksa on 29.11.2017.
 */
@WebServlet(name = "ReadPostServlet", urlPatterns = "/post")
public class ReadPostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PostService postService = new PostService();

        try {
            Post post = postService.findByID(Integer.valueOf(request.getParameter("id")));
            request.setAttribute("post", post);
            List<Post> posts = new LinkedList<>();
            posts.add(post);
            postService.transformPostDescription(posts);
        } catch (SQLException |NumberFormatException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/views/post.jsp").forward(request, response);
    }
}
