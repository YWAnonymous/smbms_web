package com.zhou.filter;

import com.zhou.pojo.User;
import com.zhou.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SysFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;


        User user = (User) httpServletRequest.getSession().getAttribute(Constants.USER_SESSION);
        System.out.println("session=============>"+httpServletRequest.getSession().getId());
        if(user == null){

            httpServletRequest.setAttribute("error","请重新登录！！");
            httpServletRequest.getRequestDispatcher("/login.jsp").forward(httpServletRequest,httpServletResponse);
        }


        chain.doFilter(httpServletRequest,httpServletResponse);

    }

    @Override
    public void destroy() {

    }
}
