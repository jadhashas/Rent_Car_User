package com.example.rent_car_user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConn {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/rent_db_user";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }
}
