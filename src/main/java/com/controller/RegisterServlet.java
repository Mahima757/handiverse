package com.controller;

import com.Utilities.PasswordUtility;
import com.Utilities.ValidationUtil;
import com.dao.UserDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ── Read form fields ──────────────────────────────────
        final String userName   = request.getParameter("username");
        final String email      = request.getParameter("email");
        final String password   = request.getParameter("password");
        final String cfPassword = request.getParameter("cpassword");

        // ── Server-side Validation ────────────────────────────

        // 1. userName: not empty, alphanumeric starting with letter, at least 5 chars
        final boolean isValidName = !ValidationUtil.isNullOrEmpty(userName)
                && ValidationUtil.isAlphanumericStartingWithLetter(userName)
                && userName.length() > 5;
        String errorUser = isValidName ? "" : "Name not Proper! ";

        // 2. Email
        final boolean isValidMail = ValidationUtil.isValidEmail(email);
        String errorMail = isValidMail ? "" : "Mail not Proper! ";

        // 3. Password: min 6 chars, 1 capital, 1 number, 1 symbol
        final boolean isValidPass = ValidationUtil.isValidPassword(password);
        String errorPass = isValidPass ? "" : "Password not Proper! ";

        // 4. Confirm password
        final boolean isValidCon = ValidationUtil.doPasswordsMatch(password, cfPassword);
        String errorCon = isValidCon ? "" : "Password not matching! ";

        String error_ = errorUser + errorMail + errorPass + errorCon;

        request.setAttribute("error",  error_);
        request.setAttribute("erUser", errorUser);
        request.setAttribute("erMail", errorMail);
        request.setAttribute("erPass", errorPass);
        request.setAttribute("erCon",  errorCon);

        // ── If validation failed, go back to register page ────
        if (!error_.isBlank()) {
            RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
            rd.forward(request, response);
            return;
        }

        // ── Hash password then save to DB ─────────────────────
        String hashedPassword = PasswordUtility.getHashPassword(password);

        final UserDAO userDAO = new UserDAO();
        int check = userDAO.addUser(userName, hashedPassword, email);
        //                          ^^^^^^^^
        //                    FIX: was "fullName" (undefined variable)
        //                         changed to "userName" which was declared above

        switch (check) {

            case 1:
                // Success — redirect to login
                response.sendRedirect("login.jsp");
                break;

            case 2:
                // Email already registered
                request.setAttribute("error", "User/Email already present!");
                RequestDispatcher rdisp = request.getRequestDispatcher("/register.jsp");
                rdisp.forward(request, response);
                break;

            default:
                // Server/DB error
                System.out.println("Server error: " + check + " :error code");
                request.setAttribute("error", "Something went wrong. Please try again.");
                RequestDispatcher rdErr = request.getRequestDispatcher("/register.jsp");
                rdErr.forward(request, response);
                break;
        }
    }
}