package com.controller;

import com.dao.ProductDAO;
import com.model.Product;
import com.model.Category;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.*;

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {

    private static final int PAGE_SIZE = 9;

    private ProductDAO productDAO = new ProductDAO();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // ── Read parameters ───────────────────────────────────
        String   keyword      = request.getParameter("search");
        String   sort         = request.getParameter("sort");
        String[] categoryParams = request.getParameterValues("category");
        String   maxPriceStr  = request.getParameter("maxPrice");
        String   pageStr      = request.getParameter("page");

        // ── Max price ─────────────────────────────────────────
        double maxPrice = 1000;
        if (maxPriceStr != null && !maxPriceStr.isEmpty()) {
            try { maxPrice = Double.parseDouble(maxPriceStr); }
            catch (NumberFormatException ignored) {}
        }

        // ── Selected categories ───────────────────────────────
        Set<Integer> selectedCatSet = new LinkedHashSet<>();
        if (categoryParams != null) {
            for (String id : categoryParams) {
                try { selectedCatSet.add(Integer.parseInt(id)); }
                catch (NumberFormatException ignored) {}
            }
        }

        // ── Pagination ────────────────────────────────────────
        int currentPage = 1;
        if (pageStr != null && !pageStr.isEmpty()) {
            try { currentPage = Math.max(1, Integer.parseInt(pageStr)); }
            catch (NumberFormatException ignored) {}
        }

        // ── Fetch data ────────────────────────────────────────
        List<Product> allProducts = productDAO.getFilteredProducts(
                keyword, selectedCatSet, maxPrice, sort);

        int totalCount = allProducts.size();                  // BUG WAS: totalCount never set
        int totalPages = Math.max(1,
                (int) Math.ceil((double) totalCount / PAGE_SIZE));

        // Manual pagination on the list
        int fromIdx = Math.min((currentPage - 1) * PAGE_SIZE, totalCount);
        int toIdx   = Math.min(fromIdx + PAGE_SIZE, totalCount);
        List<Product> products = allProducts.subList(fromIdx, toIdx);

        List<Category> categories = productDAO.getAllCategories();

        // ── Set attributes ────────────────────────────────────
        request.setAttribute("products",         products);
        request.setAttribute("categories",       categories);
        request.setAttribute("selectedCatSet",   selectedCatSet);
        request.setAttribute("keyword",          keyword);
        request.setAttribute("maxPrice",         maxPrice);
        request.setAttribute("sort",             sort != null ? sort : "newest");
        request.setAttribute("totalCount",       totalCount);   // ✅ was missing
        request.setAttribute("currentPage",      currentPage);  // ✅ was missing
        request.setAttribute("totalPages",       totalPages);   // ✅ was missing
        request.setAttribute("activePage",       "shop");

        // BUG WAS: path "/page/user/shop.jsp" — confirm this matches your project
        request.getRequestDispatcher("/page/user/shop.jsp")
               .forward(request, response);
    }
}