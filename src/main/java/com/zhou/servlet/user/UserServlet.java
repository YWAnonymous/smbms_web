package com.zhou.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.zhou.pojo.User;
import com.zhou.service.user.UserService;
import com.zhou.service.user.UserServiceImpl;
import com.zhou.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends HttpServlet {


    //修改密码
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");

        System.out.println("method----> " + method);

        if (method != null) {
            if (method.equals("savepwd")) {
                this.updatePwd(req, resp);
            } else if (method.equals("pwdmodify")) {
                this.getPwdByUserId(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void getPwdByUserId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Object o = request.getSession().getAttribute(Constants.USER_SESSION);
        Map map = new HashMap<String,String>();
        if (o != null) {
            String oldPassword = request.getParameter("oldpassword");
            if(oldPassword != null){
                if (oldPassword.equals(((User) o).getUserPassword())) {
                    map.put("result","true");
                }else{
                    map.put("result","error");
                }
            }else{
                map.put("result","false");
            }

        }else {
            map.put("result","sessionerror");
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.write(JSONArray.toJSONString(map));
        out.flush();
        out.close();
    }

    private void updatePwd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String newpassword = request.getParameter("newpassword");

        UserService service = new UserServiceImpl();
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);

        boolean flag = false;
        if (user != null && !StringUtils.isNullOrEmpty(newpassword)) {

            flag = service.updatePwd(user.getId(), newpassword);

            if (flag) {
                request.setAttribute(Constants.SYS_MESSAGE, "修改密码成功,请退出并使用新密码重新登录！");
                request.getSession().removeAttribute(Constants.USER_SESSION);//session注销
            } else {
                request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
            }
        } else {

            request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
        }

        request.getRequestDispatcher("pwdmodify.jsp").forward(request, response);
    }
}
