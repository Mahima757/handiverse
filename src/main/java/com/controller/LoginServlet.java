package com.controller;

import com.Utilities.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/login.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection conn = DBConnection.getConnection();

            if (conn == null) {
                request.setAttribute("error", "Database connection failed.");
                request.getRequestDispatcher("/WEB-INF/login.jsp")
                       .forward(request, response);
                return;
            }

            // Find user by email only
            String sql = "SELECT * FROM users WHERE Email=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                // Get hashed password from database
                String dbPassword = rs.getString("Password");

                // Compare entered password with hashed password
                if (BCrypt.checkpw(password, dbPassword)) {

                    HttpSession session = request.getSession();

                    session.setAttribute("user", rs.getString("Email"));
                    session.setAttribute("role", rs.getString("role"));

                    response.sendRedirect(request.getContextPath() + "/home");

                } else {
                    request.setAttribute("error", "Invalid credentials");
                    request.getRequestDispatcher("/WEB-INF/login.jsp")
                           .forward(request, response);
                }

            } else {
                request.setAttribute("error", "Invalid credentials");
                request.getRequestDispatcher("/WEB-INF/login.jsp")
                       .forward(request, response);
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute("error", e.getMessage());

            request.getRequestDispatcher("/WEB-INF/login.jsp")
                   .forward(request, response);
        }
    }

}