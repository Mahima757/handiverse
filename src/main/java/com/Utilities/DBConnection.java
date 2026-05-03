package com.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection conn;

    public static Connection getConnection() {

        try {

            if (conn == null || conn.isClosed()) {

                String url =
                        "jdbc:mysql://localhost:3306/handiverse_db?useSSL=false&serverTimezone=UTC";

                String user = "root";
                String password = "";

                conn = DriverManager.getConnection(url, user, password);

                System.out.println("✅ Database Connected");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}