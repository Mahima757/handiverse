package com.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.Utilities.DBConnection;
import com.dao.Interfaces.OrderDAOInterface;
import com.model.Order;

public class OrderDAO implements OrderDAOInterface {

    Connection conn = DBConnection.getConnection();

    // CREATE ORDER
    @Override
    public int createOrder(Order order) {

        int orderId = 0;

        String sql =
        "INSERT INTO orders(User_ID, Total_Amount, Status, Order_Date) " +
        "VALUES(?,?,?,NOW())";

        try {

            PreparedStatement ps =
            conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmount());
            ps.setString(3, order.getStatus());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                orderId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderId;
    }

    // GET ORDER BY ID
    @Override
    public Order getOrderById(int orderId) {

        Order order = null;
        String sql = "SELECT * FROM orders WHERE Order_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                order = new Order(
                        rs.getInt("Order_ID"),
                        rs.getInt("User_ID"),
                        rs.getTimestamp("Order_Date"),
                        rs.getDouble("Total_Amount"),
                        rs.getString("Status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }

    // USER ORDER HISTORY
    @Override
    public List<Order> getOrdersByUser(int userId) {

        List<Order> list = new ArrayList<>();

        String sql = "SELECT * FROM orders WHERE User_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new Order(
                        rs.getInt("Order_ID"),
                        rs.getInt("User_ID"),
                        rs.getTimestamp("Order_Date"),
                        rs.getDouble("Total_Amount"),
                        rs.getString("Status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ADMIN VIEW ALL ORDERS
    @Override
    public List<Order> getAllOrders() {

        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                list.add(new Order(
                        rs.getInt("Order_ID"),
                        rs.getInt("User_ID"),
                        rs.getTimestamp("Order_Date"),
                        rs.getDouble("Total_Amount"),
                        rs.getString("Status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE STATUS
    @Override
    public boolean updateOrderStatus(int orderId, String status) {

        String sql =
        "UPDATE orders SET Status=? WHERE Order_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, orderId);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    /**
     * Sum of Total_Amount across ALL orders.
     * Used for the "Total Revenue" stat card.
     */
    public double getTotalRevenue() {
        String sql = "SELECT COALESCE(SUM(Total_Amount), 0) FROM orders";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
 
    /**
     * Sum of Total_Amount for orders placed in the calendar month
     * immediately before the current one.
     * Used to compute the revenue % change badge.
     */
    public double getPreviousMonthRevenue() {
        String sql =
            "SELECT COALESCE(SUM(Total_Amount), 0) FROM orders " +
            "WHERE YEAR(Order_Date)  = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) " +
            "  AND MONTH(Order_Date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
 
    /**
     * Total number of orders ever placed.
     * Used for the "Total Orders" stat card.
     */
    public int getTotalOrderCount() {
        String sql = "SELECT COUNT(*) FROM orders";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
 
    /**
     * Number of orders placed in the previous calendar month.
     * Used to compute the orders % change badge.
     */
    public int getPreviousMonthOrderCount() {
        String sql =
            "SELECT COUNT(*) FROM orders " +
            "WHERE YEAR(Order_Date)  = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) " +
            "  AND MONTH(Order_Date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
 
    /**
     * Returns the most recent {@code limit} orders joined with the user's
     * full name so the dashboard table can display the customer name.
     *
     * Assumes your users table has columns: User_ID, Full_Name (or First_Name/Last_Name).
     * Adjust the JOIN and CONCAT below to match your actual users table column names.
     */
    public List<Order> getRecentOrders(int limit) {
        List<Order> list = new ArrayList<>();
        // Adjust "u.Full_Name" to match your users table — e.g. CONCAT(u.First_Name,' ',u.Last_Name)
        String sql =
            "SELECT o.Order_ID, o.User_ID, o.Order_Date, o.Total_Amount, o.Status, " +
            "       u.Full_Name AS Customer_Name " +
            "FROM orders o " +
            "LEFT JOIN users u ON o.User_ID = u.User_ID " +
            "ORDER BY o.Order_Date DESC " +
            "LIMIT ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("Order_ID"),
                    rs.getInt("User_ID"),
                    rs.getTimestamp("Order_Date"),
                    rs.getDouble("Total_Amount"),
                    rs.getString("Status")
                );
                order.setCustomerName(rs.getString("Customer_Name"));
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
 
    /**
     * Returns an int[4] representing the order count for each of the
     * four weeks in the current calendar month.
     *
     *   index 0 → days  1–7
     *   index 1 → days  8–14
     *   index 2 → days 15–21
     *   index 3 → days 22–end
     *
     * Used to draw the Sales Growth line chart in the dashboard.
     */
    public int[] getWeeklySales() {
        int[] weeks = new int[4];
        String sql =
            "SELECT " +
            "  SUM(CASE WHEN DAY(Order_Date) BETWEEN  1 AND  7  THEN 1 ELSE 0 END) AS week1, " +
            "  SUM(CASE WHEN DAY(Order_Date) BETWEEN  8 AND 14  THEN 1 ELSE 0 END) AS week2, " +
            "  SUM(CASE WHEN DAY(Order_Date) BETWEEN 15 AND 21  THEN 1 ELSE 0 END) AS week3, " +
            "  SUM(CASE WHEN DAY(Order_Date) >= 22               THEN 1 ELSE 0 END) AS week4  " +
            "FROM orders " +
            "WHERE YEAR(Order_Date)  = YEAR(CURRENT_DATE) " +
            "  AND MONTH(Order_Date) = MONTH(CURRENT_DATE)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                weeks[0] = rs.getInt("week1");
                weeks[1] = rs.getInt("week2");
                weeks[2] = rs.getInt("week3");
                weeks[3] = rs.getInt("week4");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weeks;
}
}