package com.controller;

import com.dao.OrderDAO;
import com.dao.ProductDAO;
import com.dao.UserDAO;
import com.model.Order;
import com.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * AdminDashboardServlet
 *
 * Mapped to:  /adminDashboard
 *
 * Responsibilities
 * ─────────────────
 *  1. Guard the route – redirect non-admin users to the login page.
 *  2. Fetch all dashboard metrics from the DAO layer.
 *  3. Forward data to adminDashboard.jsp for rendering.
 *
 * Expected DAO methods (add these to your DAO classes if missing):
 *  - OrderDAO.getTotalRevenue()            → double
 *  - OrderDAO.getTotalOrderCount()         → int
 *  - OrderDAO.getRecentOrders(int limit)   → List<Order>  (joined with user name)
 *  - OrderDAO.getWeeklySales()             → int[4]       (Week1..Week4 order counts)
 *  - OrderDAO.getPreviousMonthRevenue()    → double       (for % change calc)
 *  - OrderDAO.getPreviousMonthOrderCount() → int
 *  - ProductDAO.getTopSellingProducts(int limit) → List<Product> (sorted by sold qty desc)
 *  - ProductDAO.getActiveListingCount()   → int
 *  - UserDAO.getNewCustomerCount()        → int          (e.g. last 30 days)
 *  - UserDAO.getPreviousMonthNewCustomers()→ int
 */
@WebServlet("/dashboard")
public class AdmindashboardServlet extends HttpServlet {

    // ── DAO instances ──────────────────────────────────────────────────────────
    private OrderDAO   orderDAO;
    private ProductDAO productDAO;
    private UserDAO    userDAO;

    @Override
    public void init() throws ServletException {
        orderDAO   = new OrderDAO();
        productDAO = new ProductDAO();
        userDAO    = new UserDAO();
    }

    // ── GET ────────────────────────────────────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ── 1. Admin authentication guard ─────────────────────────────────────
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ── 2. Fetch metrics ───────────────────────────────────────────────────
        try {
            // Revenue
            double currentRevenue  = orderDAO.getTotalRevenue();
            double previousRevenue = orderDAO.getPreviousMonthRevenue();
            double revenueChange   = computePercentChange(previousRevenue, currentRevenue);

            // Orders
            int currentOrders  = orderDAO.getTotalOrderCount();
            int previousOrders = orderDAO.getPreviousMonthOrderCount();
            double ordersChange = computePercentChange(previousOrders, currentOrders);

            // Customers
            int currentCustomers  = userDAO.getNewCustomerCount();
            int previousCustomers = userDAO.getPreviousMonthNewCustomers();
            double customersChange = computePercentChange(previousCustomers, currentCustomers);

            // Active product listings
            int activeListings = productDAO.getActiveListingCount();

            // Weekly sales for chart (int[4])
            int[] weeklySales = orderDAO.getWeeklySales();
            if (weeklySales == null || weeklySales.length < 4) {
                weeklySales = new int[]{0, 0, 0, 0};
            }

            // Top-selling products (limit 3 to match design)
            List<Product> topProducts = productDAO.getTopSellingProducts(3);

            // Recent orders (limit 10)
            List<Order> recentOrders = orderDAO.getRecentOrders(10);

            // ── 3. Set request attributes ─────────────────────────────────────
            req.setAttribute("totalRevenue",    currentRevenue);
            req.setAttribute("totalOrders",     currentOrders);
            req.setAttribute("newCustomers",    currentCustomers);
            req.setAttribute("activeListings",  activeListings);

            req.setAttribute("revenueChange",   revenueChange);
            req.setAttribute("ordersChange",    ordersChange);
            req.setAttribute("customersChange", customersChange);

            req.setAttribute("weeklySales",     weeklySales);
            req.setAttribute("topProducts",     topProducts);
            req.setAttribute("recentOrders",    recentOrders);

        } catch (Exception e) {
            // Log error and forward with empty/zero defaults so the page still renders
            getServletContext().log("AdminDashboardServlet – DAO error", e);

            req.setAttribute("totalRevenue",    0.0);
            req.setAttribute("totalOrders",     0);
            req.setAttribute("newCustomers",    0);
            req.setAttribute("activeListings",  0);
            req.setAttribute("revenueChange",   0.0);
            req.setAttribute("ordersChange",    0.0);
            req.setAttribute("customersChange", 0.0);
            req.setAttribute("weeklySales",     new int[]{0, 0, 0, 0});
            req.setAttribute("topProducts",     List.of());
            req.setAttribute("recentOrders",    List.of());
            req.setAttribute("dashboardError",  "Could not load dashboard data. Please try again.");
        }

        // ── 4. Forward to JSP ─────────────────────────────────────────────────
        req.getRequestDispatcher("/page/admin/dashboard.jsp")
           .forward(req, resp);
    }

    // ── POST (not used – redirect to GET) ─────────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    /**
     * Returns the percentage change from {@code previous} to {@code current}.
     * Returns 0 when previous is 0 to avoid division-by-zero.
     */
    private double computePercentChange(double previous, double current) {
        if (previous == 0) return 0.0;
        return ((current - previous) / previous) * 100.0;
    }
}
