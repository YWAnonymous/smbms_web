package com.zhou.dao.role;

import com.zhou.dao.BaseAction;
import com.zhou.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    @Override
    public List<Role> getRoleList(Connection connection) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Role> roleList = new ArrayList<Role>();
        if (connection != null) {
            String sql = "select * from smbms_role";
            Object[] params = {};
            rs = BaseAction.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                Role _role = new Role();
                _role.setId(rs.getInt("id"));
                _role.setRoleCode(rs.getString("roleCode"));
                _role.setRoleName(rs.getString("roleName"));
                roleList.add(_role);
            }
            BaseAction.close(null, pstm, rs);
        }
        return roleList;
    }
}
