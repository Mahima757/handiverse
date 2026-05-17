package com.ingcollegeapt.week4twebapp.controller.servlets;

import com.ingcollegeapt.week4twebapp.dao.UserDAO;
import com.ingcollegeapt.week4twebapp.model.User;
import com.ingcollegeapt.week4twebapp.utilities.PasswordUtil;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("username");
        String typedPassword = request.getParameter("password");
        UserDAO userDao = new UserDAO();
        User user = userDao.getUser(userName);
        //if no user found in database send error message
        System.out.println(user.getPassword());
        if (user == null) {
            request.setAttribute("error", "user or password mismatch!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        } else {
            String hashedPassword = user.getPassword();
            boolean matched = PasswordUtil.checkPassword(typedPassword, hashedPassword);
            //if user and password matched, redirect to topiclist
            if (matched) {
                //view all topics is handled by doGet so we redirect to /topic endpoint
//                response.sendRedirect("topic");
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
