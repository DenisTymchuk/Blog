package com.company.filters;

import com.company.entity.User;
import com.company.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Oksa on 30.11.2017.
 */
@WebFilter(urlPatterns = {"/post", "/editPost", "/readPost", "/deletePost", "/newPost", "/posts", "/myPosts",
        "/sortAllPosts", "/sortMyPosts", "/search", "/searchMyPosts"})
public class LoginRequiredFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = MyUtils.getLoginedUser(request.getSession());
        request.setAttribute("isUserLogined", user != null);
        if (user != null) {
            request.setAttribute("nameLoginedUser", user.getName());
            request.setAttribute("loginedUser", user);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
