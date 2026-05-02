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
}