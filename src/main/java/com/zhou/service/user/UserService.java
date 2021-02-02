package com.zhou.service.user;

import com.zhou.pojo.User;

import java.util.List;

public interface UserService {
    //  登录
    public User login(String userCode,String password);
    // 更新密码
    public boolean updatePwd(long id, String newUserPassword);
    // 获取用户列表
    public List<User> getUserList(String userName, int userRole,int currentPageNo, int pageSize);
    // 获取数据总数
    public int getUserCount(String userName, int userRole);
}
