package com.zhou.dao.user;

import com.zhou.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public interface UserDao {

    // 登录获取账户
    public User getLoginUser(Connection connection, String userCode) throws Exception;

    // 修改密码
    public int updatePwd(Connection connection, String newUserPassword,long id) throws Exception;

    // 查询用户列表
    //增加分页 int currentPageNo, int pageSize
    public List<User> getUserList(Connection connection,String userName,int userRole,int currentPageNo, int pageSize) throws Exception;

    // 获取数据总数
    public int getUserCount(Connection connection,String userName,int userRole) throws Exception;

}
