package com.company.servlets;

import com.company.entity.User;
import com.company.service.UserService;
import com.company.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Oksa on 27.11.2017.
 */
@WebServlet(urlPatterns = {"/registr"})
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        UserService userService = new UserService();
        String login = request.getParameter("userName").trim();
        String password = request.getParameter("password").trim();
        String confirmPassword = request.getParameter("confirmPassword").trim();
        User user = null;
        User userWithId = null;
        Map<Integer, String > errors = null;

        try {
            errors = userService.userRegistrValidation(login, password, confirmPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!errors.isEmpty()) {
            String error = errors.get(1) != null ? errors.get(1) : errors.get(2) != null ? errors.get(2) : errors.get(3);
            response.setContentType("text/plain");
            response.getWriter().print(error);
        } else {
            try {
                user = new User();
                user.setName(login);
                user.setPassword(password);
                userService.insertUser(user); //When we insert user in database he get unique id, but in class user id = 0
                userWithId = userService.findByLogin(user.getName()); //here we get user from database, and set userID = ID in database
            } catch (SQLException e) {
                e.printStackTrace();
            }
            HttpSession session = request.getSession();
            MyUtils.storeLoginedUser(session, userWithId);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registration.jsp");
        dispatcher.forward(request, response);
    }
}
