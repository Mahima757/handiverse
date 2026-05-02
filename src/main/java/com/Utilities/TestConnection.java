package com.Utilities;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Connection conn = DBConnection.getConnection();

        if(conn != null) {
            System.out.println("Connection Successful!");
        } else {
            System.out.println("Connection Failed!");
        }
    }
}