package com.company.servlets;

import com.company.entity.Category;
import com.company.entity.Post;
import com.company.entity.User;
import com.company.service.CategoryService;
import com.company.service.PostService;
import com.company.utils.MyUtils;

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
@WebServlet(name = "EditPostServlet", urlPatterns = "/editPost")
@MultipartConfig
public class EditPostServlet extends HttpServlet {
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

            Part filePart = request.getPart("file");
            String appPath = request.getServletContext().getRealPath("");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String image_link = "";
            if(fileName == null || fileName.equals("")){
                image_link = postService.findByID(Long.valueOf(request.getParameter("id"))).getImageLink();
            }else {
                filePart.write(appPath + "../pictures/" + fileName);
                image_link = "../pictures/" + fileName;
            }
            post.setImageLink(image_link);

            post.setId(Long.valueOf(request.getParameter("id")));
            post.setUserCreator(MyUtils.getLoginedUser(request.getSession()));
            categoryService.deleteAllCategorisByPost(post);
            postService.setCategoryForPost(request.getParameterValues("multipleSelect"), post);
            postService.updatePost(post);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getParameter(request.getServletContext().getContextPath() + "previousPage"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PostService postService = new PostService();
        try {
            request.setAttribute("categoryList", categoryService.findAllCategories());
            request.setAttribute("post", postService.findByID(Long.valueOf(request.getParameter("id"))));
        } catch (SQLException|NumberFormatException e) {
            e.printStackTrace();
        }
        request.setAttribute("previousPage", request.getHeader("referer").substring(22));
        request.getRequestDispatcher("/WEB-INF/views/editPost.jsp").forward(request, response);
    }
}
