package com.zhou.dao;

/*
jdbc公共类
 */

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseAction {
    private static String driver = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;

    static {
        try {
            //读取properties中的数据
            InputStream is = BaseAction.class.getClassLoader().getResourceAsStream("db.properties");
            Properties prop = new Properties();
            prop.load(is);
            driver = prop.getProperty("driver");
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


    public static ResultSet execute(Connection connection, PreparedStatement pstm, ResultSet rs,String sql, Object[] params) {
        try {
            pstm = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstm.setObject(i + 1, params[i]);
            }
            rs = pstm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static int executeUpdate(String sql, Object[] params, Connection conn, PreparedStatement pstmt) {
        int UpdateRow = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            UpdateRow = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UpdateRow;
    }

    //关闭资源
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {

        if (resultSet != null) {
            try {
                resultSet.close();
                resultSet = null;//GC回收
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    public static void main(String[] args) throws Exception {

      //读取properties中的数据
        InputStream is = BaseAction.class.getClassLoader().getResourceAsStream("db.properties");
        Properties prop = new Properties();
        prop.load(is);

        String driver = prop.getProperty("driver");
        String url = prop.getProperty("url");
        String username = prop.getProperty("username");
        String password = prop.getProperty("password");


        Class.forName(driver);

        Connection connection = DriverManager.getConnection(url, username, password);
        //预编译sql
        String sql = "select * from smbms_user where userCode = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, "admin");

        ResultSet rs = statement.executeQuery();


        while (rs.next()) {
            Object userName = rs.getObject("userName");
            System.out.println(userName);
        }

        rs.close();
        statement.close();
        connection.close();
    }

}
