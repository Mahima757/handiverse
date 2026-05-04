package com.controller;

import com.Utilities.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/register.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection conn = DBConnection.getConnection();

            if (conn == null) {
                request.setAttribute("error", "Database connection failed.");
                request.getRequestDispatcher("/WEB-INF/register.jsp")
                       .forward(request, response);
                return;
            }

            // Check whether email already exists
            String checkSql = "SELECT * FROM users WHERE Email = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, email);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                request.setAttribute("error", "This email is already registered. Please login.");
                request.getRequestDispatcher("/WEB-INF/register.jsp")
                       .forward(request, response);
                return;
            }

            // Insert new user
            String insertSql = "INSERT INTO users (Full_Name, Email, Password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertSql);

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, "USER");

            int rows = ps.executeUpdate();

            if (rows > 0) {
                request.setAttribute("success", "Registration successful. Please login.");
                request.getRequestDispatcher("/WEB-INF/login.jsp")
                       .forward(request, response);
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("/WEB-INF/register.jsp")
                       .forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("error", "Server error. Please check database connection and column names.");
            request.getRequestDispatcher("/WEB-INF/register.jsp")
                   .forward(request, response);
        }
    }
}