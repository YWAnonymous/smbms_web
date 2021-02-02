package com.zhou.service.role;

import com.zhou.dao.BaseAction;
import com.zhou.dao.role.RoleDao;
import com.zhou.dao.role.RoleDaoImpl;
import com.zhou.pojo.Role;

import java.sql.Connection;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }

    @Override
    public List<Role> getRoleList() {

        Connection connection = null;
        List<Role> roleList = null;

        try {
            connection = BaseAction.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseAction.close(connection, null, null);
        }
        return roleList;
    }
}
