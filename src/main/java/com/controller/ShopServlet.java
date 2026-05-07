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

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {

    private static final int PAGE_SIZE = 9; // products per page

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO  productDAO  = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        // ── Category checkboxes (multiple values: ?category=1&category=3) ──
        String[] categoryParams = request.getParameterValues("category");
        int[] categoryIds = null;

        if (categoryParams != null && categoryParams.length > 0) {
            categoryIds = new int[categoryParams.length];
            for (int i = 0; i < categoryParams.length; i++) {
                try {
                    categoryIds[i] = Integer.parseInt(categoryParams[i]);
                } catch (NumberFormatException e) {
                    categoryIds[i] = 0;
                }
            }
        }

        // ── Price range ──────────────────────────────────────
        double minPrice = 0;
        double maxPrice = 10000;

        try {
            String minParam = request.getParameter("minPrice");
            if (minParam != null && !minParam.isEmpty())
                minPrice = Double.parseDouble(minParam);
        } catch (NumberFormatException ignored) {}

        try {
            String maxParam = request.getParameter("maxPrice");
            if (maxParam != null && !maxParam.isEmpty())
                maxPrice = Double.parseDouble(maxParam);
        } catch (NumberFormatException ignored) {}

        // ── Sort ─────────────────────────────────────────────
        String sort = request.getParameter("sort");
        if (sort == null) sort = "newest";

        // ── Search keyword ───────────────────────────────────
        String keyword = request.getParameter("search");

        // ── Pagination ───────────────────────────────────────
        int currentPage = 1;
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty())
                currentPage = Integer.parseInt(pageParam);
            if (currentPage < 1) currentPage = 1;
        } catch (NumberFormatException ignored) {}

        int offset = (currentPage - 1) * PAGE_SIZE;

        // ── Fetch filtered products ──────────────────────────
        List<Product> products;
        int totalCount;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Keyword search overrides other filters for simplicity
            products   = productDAO.searchProducts(keyword.trim());
            totalCount = products.size();
            // Manual pagination on the list
            int fromIdx = Math.min(offset, products.size());
            int toIdx   = Math.min(offset + PAGE_SIZE, products.size());
            products    = products.subList(fromIdx, toIdx);
        } else {
            products   = productDAO.getFilteredProducts(
                             categoryIds, minPrice, maxPrice, sort, offset, PAGE_SIZE);
            totalCount = productDAO.getFilteredProductCount(
                             categoryIds, minPrice, maxPrice);
        }

        // ── Pagination calculations ──────────────────────────
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);
        if (totalPages < 1) totalPages = 1;

        // ── All categories for sidebar ───────────────────────
        List<Category> categories = categoryDAO.getAllCategories();

        // ── Pass selected category IDs back as a set for checkbox checked state
        java.util.Set<Integer> selectedCatSet = new java.util.HashSet<>();
        if (categoryIds != null) {
            for (int id : categoryIds) selectedCatSet.add(id);
        }

        // ── Set attributes ────────────────────────────────────
        request.setAttribute("products",        products);
        request.setAttribute("categories",      categories);
        request.setAttribute("selectedCatSet",  selectedCatSet);
        request.setAttribute("minPrice",        minPrice);
        request.setAttribute("maxPrice",        maxPrice == 10000 ? "" : maxPrice);
        request.setAttribute("sort",            sort);
        request.setAttribute("keyword",         keyword);
        request.setAttribute("currentPage",     currentPage);
        request.setAttribute("totalPages",      totalPages);
        request.setAttribute("totalCount",      totalCount);
        request.setAttribute("pageSize",        PAGE_SIZE);
        request.setAttribute("activePage",      "shop");

        request.getRequestDispatcher("page/user/shop.jsp")
               .forward(request, response);
    }
}