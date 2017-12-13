package com.company.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Oksa on 06.12.2017.
 */
@WebFilter(urlPatterns = {"/newPost", "/myPosts", "/searchMyPosts"})
public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest HTTPrequest = (HttpServletRequest) request;
        HttpServletResponse HTTPresponse = (HttpServletResponse) response;

        if (HTTPrequest.getSession().getAttribute("loginedUser") != null) {
            chain.doFilter(request, response);
        } else {
            HTTPresponse.sendRedirect("/posts");
        }
    }

    @Override
    public void destroy() {

    }
}
