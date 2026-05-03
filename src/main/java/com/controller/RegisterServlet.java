package com.controller;

import com.Utilities.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/register.jsp")
               .forward(request, response);
        System.out.println("REGISTER PAGE LOADED");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    		System.out.println("REGISTER SERVLET HIT");

    	    String name = request.getParameter("name");
    	    String email = request.getParameter("email");
    	    String password = request.getParameter("password");

    	    System.out.println("Name: " + name);
    	    System.out.println("Email: " + email);

        try {
            Connection conn = DBConnection.getConnection();

            // check if user already exists
            String checkSql = "SELECT * FROM users WHERE email=?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, email);

            ResultSet rs = checkPs.executeQuery();
           

            if (rs.next()) {
                request.setAttribute("error", "Email already exists");
                request.getRequestDispatcher("/WEB-INF/register.jsp")
                       .forward(request, response);
                return;
            }

            // insert new user
            String sql = "INSERT INTO users (Full_Name, Email, Password, role) VALUES (?, ?, ?, 'CUSTOMER')";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            int result = ps.executeUpdate();
            System.out.println("Insert result: " + result);

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                request.setAttribute("error", "Registration failed");
                request.getRequestDispatcher("/WEB-INF/register.jsp")
                       .forward(request, response);
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
