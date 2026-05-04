package com.controller;

import com.dao.CategoryDAO;
import com.dao.ProductDAO;
import com.model.Category;
import com.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO  productDAO  = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        // Featured products — show only first 4
        List<Product> all = productDAO.getAllProducts();
        List<Product> featuredProducts = all.size() > 4 ? all.subList(0, 4) : all;

        // Categories for Curated Collections section
        List<Category> categories = categoryDAO.getAllCategories();

        request.setAttribute("featuredProducts", featuredProducts);
        request.setAttribute("categories",categories);
        request.setAttribute("activePage","home");

        request.getRequestDispatcher("/page/user/home.jsp").forward(request, response);
    }
}