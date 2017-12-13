package com.company.servlets;

import com.company.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Oksa on 30.11.2017.
 */
@WebServlet(name = "DeletePostServlet", urlPatterns = "/deletePost")
public class DeletePostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostService postService = new PostService();
        try {
            postService.deletePostById(Long.valueOf(request.getParameter("id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
