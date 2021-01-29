package com.zhou.servlet.user;

import com.zhou.pojo.User;
import com.zhou.service.user.UserService;
import com.zhou.service.user.UserServiceImpl;
import com.zhou.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        //调用service方法，进行用户匹配
        UserService userService = new UserServiceImpl();

        User user = userService.login(userCode, userPassword);

        if(user != null){
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            resp.sendRedirect("jsp/frame.jsp");
        }else{
            req.setAttribute("error","用户名或密码不正确");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
