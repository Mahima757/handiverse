package com.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection conn;

    public static Connection getConnection() {

        try {
            if (conn == null || conn.isClosed()) {

                Class.forName("com.mysql.cj.jdbc.Driver");

                String url = "jdbc:mysql://localhost:3306/handiverse?useSSL=false&serverTimezone=UTC";
                String user = "root";
                String password = "";

                conn = DriverManager.getConnection(url, user, password);

                System.out.println("Database connected successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}