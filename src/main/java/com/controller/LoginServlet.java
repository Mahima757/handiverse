package com.controller;

import com.Utilities.CookieUtil;
import com.Utilities.DBConnection;
import com.Utilities.PasswordUtility;
import com.Utilities.SessionUtil;
import com.dao.UserDAO;
import com.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("username");
        String typedPassword = request.getParameter("password");
        UserDAO userDao = new UserDAO();
        User user = userDao.getUser(userName);
        //if no user found in database send error message
        if (user == null) {
            request.setAttribute("error", "user or password mismatch!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        } else {
            String hashedPassword = user.getPassword();
            boolean matched = PasswordUtility.checkPassword(typedPassword, hashedPassword);
            //if user and password matched, redirect to topiclist
            if (matched) {
                // we store user object in session with user attribute
                SessionUtil.setAttribute(request, "user", user);
                // in seconds sec*min*hr=total sec
                CookieUtil.addCookie(response, "name", user.getFullName(), 60*60*24);// 1 day
                CookieUtil.addCookie(response, "id", String.valueOf(user.getUserId()), 60*60*24);// 1 day
                //view all topics is handled by doGet so we redirect to /topic endpoint
                // response.sendRedirect("topic");
                response.sendRedirect(request.getContextPath() + "/topic");
            } else {
                //if password is mismatched, send error message to login page
                request.setAttribute("error", "user or password mismatch!");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            }
        }

    }

}
