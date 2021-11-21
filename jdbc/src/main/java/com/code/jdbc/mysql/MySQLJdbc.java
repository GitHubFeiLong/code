package com.code.jdbc.mysql;

import java.sql.*;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.mysql.cj.result.Field;;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/11/21 17:01
 */
public class MySQLJdbc {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
                "root",
                "l(=8gp_04h*&"
        );
        String sql = "select id, name, age from user";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = (ResultSetMetaData)resultSet.getMetaData();
        Field[] fields = metaData.getFields();
        for (int i = 0; i < fields.length; i++) {
            System.out.println("fields = " + fields[i].getMysqlType());
        }

        while (resultSet.next()) {
            int columnCount = metaData.getColumnCount();
            System.out.println(resultSet.getInt("id"));
            System.out.println(resultSet.getString("name"));
            System.out.println(resultSet.getInt("age"));
        }



    }
}
