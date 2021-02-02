package com.zhou.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.zhou.pojo.Role;
import com.zhou.pojo.User;
import com.zhou.service.role.RoleService;
import com.zhou.service.role.RoleServiceImpl;
import com.zhou.service.user.UserService;
import com.zhou.service.user.UserServiceImpl;
import com.zhou.util.Constants;
import com.zhou.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServlet extends HttpServlet {


    //修改密码
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");

        System.out.println("method----> " + method);

        if (method != null) {
            // 修改密码
            if (method.equals("savepwd")) {
                this.updatePwd(req, resp);
            // 验证原密码是否正确
            } else if (method.equals("pwdmodify")) {
                this.getPwdByUserId(req, resp);
            // 用户页面查询
            }else if(method.equals("query")){
                this.query(req,resp);
            }
        }
    }

    private void query(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        // 准备数据
        String queryname = request.getParameter("queryname");
        String userRole = request.getParameter("queryUserRole");
        String pageIndex = request.getParameter("pageIndex");

        int queryUserRole = 0;
        if(userRole != null && userRole != ""){
            queryUserRole = Integer.parseInt(userRole);
        }

        // 发送到后台
        UserService userService = new UserServiceImpl();
        RoleService roleService = new RoleServiceImpl();

        List<Role> roleList = roleService.getRoleList();
        int totalCount = userService.getUserCount(queryname, queryUserRole);
        //-----------------分页开始---------------------------------------
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;
        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                response.sendRedirect("error.jsp");
            }
        }


        PageSupport pages = new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        int totalPageCount = pages.getTotalPageCount();

        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }
        // 增加分页条件
        List<User> userList = userService.getUserList(queryname, queryUserRole,currentPageNo,pageSize);

        request.setAttribute("totalPageCount", totalPageCount);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPageNo", currentPageNo);

        // 传值到页面
        request.setAttribute("queryUserRole",queryUserRole);
        request.setAttribute("queryUserName",queryname);
        request.setAttribute("userList",userList);
        request.setAttribute("roleList",roleList);

        request.getRequestDispatcher("userlist.jsp").forward(request,response);
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


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
