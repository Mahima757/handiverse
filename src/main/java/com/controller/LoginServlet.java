package com.controller;

import com.Utilities.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

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
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }

            String sql = "SELECT * FROM users WHERE Email=? AND Password=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("user", rs.getString("email"));
                session.setAttribute("role", rs.getString("role"));

                response.sendRedirect(request.getContextPath() + "/home"); 

            } else {
                request.setAttribute("error", "Invalid credentials");
                request.getRequestDispatcher("/home.jsp")
                       .forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Server error");
            request.getRequestDispatcher("/WEB-INF/login.jsp")
                   .forward(request, response);  
        }
    }
}