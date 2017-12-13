package com.company.servlets;

import com.company.entity.User;
import com.company.service.UserService;
import com.company.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Oksa on 27.11.2017.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        UserService userService = new UserService();
        User user = null;
        String userName = request.getParameter("userName").trim();
        String password = request.getParameter("password").trim();

        Map<Integer, String> errors = null;
        try {
            errors = userService.userLoginValidation(userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!errors.isEmpty()) {
            String error = errors.get(1) != null ? errors.get(1) : errors.get(2);
            response.setContentType("text/plain");
            response.getWriter().print(error);
        } else {
            try {
                user = userService.findByLogin(userName);
                MyUtils.storeLoginedUser(request.getSession(), user);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if ("Y".equals(request.getParameter("rememberMe"))) {
                MyUtils.storeUserCookie(response, user);
            }
            else {
                MyUtils.deleteUserCookie(response);
            }
        }
    }
}
