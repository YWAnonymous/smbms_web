package com.zhou.dao.user;

import com.zhou.pojo.User;
import java.sql.Connection;

public interface UserDao {

    // 登录获取账户
    public User getLoginUser(Connection connection, String userCode) throws Exception;

}
