package com.zhou.service.user;

import com.zhou.dao.BaseAction;
import com.zhou.dao.user.UserDao;
import com.zhou.dao.user.UserDaoImpl;
import com.zhou.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }


    @Override
    public int getUserCount(String userName, int userRole) {
        Connection connection = null;
        int count = 0;
        try {
            connection = BaseAction.getConnection();
            count = userDao.getUserCount(connection, userName, userRole);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            BaseAction.close(connection,null,null);
        }

        return count;
    }

    @Override
    public List<User> getUserList(String userName, int userRole,int currentPageNo, int pageSize) {

        Connection connection = null;
        List<User> userList = null;

        try {
            connection = BaseAction.getConnection();
            userList = userDao.getUserList(connection, userName, userRole,currentPageNo,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            BaseAction.close(connection,null,null);
        }
        return userList;
    }

    @Override
    public boolean updatePwd(long id, String newUserPassword) {

        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseAction.getConnection();
            if(userDao.updatePwd(connection, newUserPassword,id) >0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            BaseAction.close(connection,null,null);
        }
        return flag;
    }

    @Override
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;
        try {
            connection = BaseAction.getConnection();
            user = userDao.getLoginUser(connection, userCode);

            if(!password.equals(user.getUserPassword())){
                user = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            BaseAction.close(connection,null,null);
        }
        return user;
    }

}
