package com.zhou.dao.role;

import com.zhou.pojo.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleDao {

    // 获取role 列表
    public List<Role> getRoleList(Connection connection) throws Exception;


}
