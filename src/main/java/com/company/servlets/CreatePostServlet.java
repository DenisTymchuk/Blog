package com.company.servlets;

import com.company.entity.Category;
import com.company.entity.Post;
import com.company.entity.User;
import com.company.service.CategoryService;
import com.company.service.PostService;
import com.company.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

/**
 * Created by Oksa on 29.11.2017.
 */
@WebServlet(name = "CreatePostServlet", urlPatterns = "/newPost")
@MultipartConfig
public class CreatePostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PostService postService = new PostService();
        CategoryService categoryService = new CategoryService();
        Post post = new Post();

        try {
            post.setDescription(request.getParameter("description"));
            post.setTittle(request.getParameter("tittle"));
            if (request.getParameter("published") == null ) {
                post.setPublished(false);
            } else {
                post.setPublished(true);
            }
            post.setUserCreator(MyUtils.getLoginedUser(request.getSession()));

            Part filePart = request.getPart("file");
            String appPath = request.getServletContext().getRealPath("");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            if (!fileName.equals("")) {
                filePart.write(appPath + "../pictures/" + fileName);
                String image_link = "../pictures/" + fileName;
                post.setImageLink(image_link);
            }
            long id = postService.insertPost(post);
            post.setId(id);
            postService.setCategoryForPost(request.getParameterValues("multipleSelect"), post);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getServletContext().getContextPath() + "/posts");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            request.setAttribute("categoryList", categoryService.findAllCategories());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/addPost.jsp");
        dispatcher.forward(request, response);
    }
}

