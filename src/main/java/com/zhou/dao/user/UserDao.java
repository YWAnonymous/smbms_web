package com.zhou.dao.user;

import com.zhou.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;

public interface UserDao {

    // 登录获取账户
    public User getLoginUser(Connection connection, String userCode) throws Exception;


    public int updatePwd(Connection connection,  String newUserPassword,long id) throws Exception;

}
