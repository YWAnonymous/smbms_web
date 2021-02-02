package com.zhou.dao.user;

import com.mysql.jdbc.StringUtils;
import com.zhou.dao.BaseAction;
import com.zhou.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {


    @Override
    public int getUserCount(Connection connection, String userName, int userRole) throws Exception {

        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if (connection != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");

            List<Object> list = new ArrayList<>();
            if (!StringUtils.isNullOrEmpty(userName)) {
                sb.append(" and userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sb.append(" and userRole = ?");
                list.add(userRole);
            }
            Object[] params = list.toArray();
            System.out.println("sql--getUserCount----> " + sb.toString());
            rs = BaseAction.execute(connection, pstm, rs, sb.toString(), params);
            if (rs.next()) {
                count = rs.getInt("count");
            }
            BaseAction.close(null, pstm, rs);
        }

        return count;
    }

    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception {

        PreparedStatement pstm = null;
        ResultSet rs = null;

        List<User> userList = new ArrayList<User>();

        if (connection != null) {
            // 拼接sql
            StringBuffer sql = new StringBuffer();
            // u.userCode,u.userName,u.gender,u.phone,r.roleName
            sql.append("select * from smbms.smbms_user u,smbms.smbms_role r where u.userRole = r.id");

            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }

            sql.append(" limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);
            Object[] params = list.toArray();

            System.out.println("sql--getUserList----> " + sql.toString());
            rs = BaseAction.execute(connection, pstm, rs, sql.toString(), params);

            while (rs.next()) {
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString("userName"));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setPhone(rs.getString("phone"));
                _user.setUserRole(rs.getInt("userRole"));
                _user.setUserRoleName(rs.getString("roleName"));
                userList.add(_user);
            }
            BaseAction.close(null, pstm, rs);
        }
        return userList;

    }

    @Override
    public int updatePwd(Connection connection, String newUserPassword, long id) throws Exception {
        PreparedStatement pstm = null;
        int row = 0;
        if (connection != null) {
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object params[] = {newUserPassword, id};
            row = BaseAction.executeUpdate(sql, params, connection, pstm);
            System.out.println("row======>" + row);
            BaseAction.close(null, pstm, null);
        }
        return row;
    }

    @Override
    public User getLoginUser(Connection connection, String userCode) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if (connection != null) {
            String sql = "select * from smbms_user where userCode = ?";
            Object params[] = {userCode};
            rs = BaseAction.execute(connection, pstm, rs, sql, params);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            BaseAction.close(null, pstm, rs);
        }
        return user;
    }
}
