package com.zhou.service.user;

import com.zhou.pojo.User;

public interface UserService {
    //  登录
    public User login(String userCode,String password);
    // 更新密码
    public boolean updatePwd(long id, String newUserPassword);
}
