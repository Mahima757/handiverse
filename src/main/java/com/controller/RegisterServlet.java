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

    // JSP location: src/main/webapp/WEB-INF/pages/register.jsp
    private static final String REGISTER_JSP = "/WEB-INF/register.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
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

        // 1. Name: not empty, alphanumeric starting with letter, at least 5 chars
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

        // Pass errors and repopulate fields
        request.setAttribute("error",     error_);
        request.setAttribute("erUser",    errorUser);
        request.setAttribute("erMail",    errorMail);
        request.setAttribute("erPass",    errorPass);
        request.setAttribute("erCon",     errorCon);
        request.setAttribute("prevName",  userName);
        request.setAttribute("prevEmail", email);

        // ── Validation failed — go back to register page ──────
        if (!error_.isBlank()) {
            request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
            return;
        }

        // ── Hash password and save ────────────────────────────
        String hashedPassword = PasswordUtility.getHashPassword(password);

        UserDAO userDAO = new UserDAO();
        int check = userDAO.addUser(userName, hashedPassword, email);

        switch (check) {

            case 1:
                // Success — redirect to login
                response.sendRedirect(request.getContextPath() + "/login");
                break;

            case 2:
                // Email already registered
                request.setAttribute("error", "User/Email already present!");
                request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
                break;

            default:
                // Server/DB error
                request.setAttribute("error", "Something went wrong. Please try again.");
                request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
                break;
        }
    }
}