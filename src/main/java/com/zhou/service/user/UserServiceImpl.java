package com.zhou.service.user;

import com.zhou.dao.BaseAction;
import com.zhou.dao.user.UserDao;
import com.zhou.dao.user.UserDaoImpl;
import com.zhou.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
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


    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        Connection connection = BaseAction.getConnection();

        try {
            User user = userDao.getLoginUser(connection, "admin");
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
