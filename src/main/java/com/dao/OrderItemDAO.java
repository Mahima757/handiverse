package com.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.Utilities.DBConnection;
import com.dao.Interfaces.OrderItemDAOInterface;
import com.model.OrderItem;

public class OrderItemDAO implements OrderItemDAOInterface {

    Connection conn = DBConnection.getConnection();

    // ADD ITEM
    @Override
    public boolean addOrderItem(OrderItem item) {

        String sql =
        "INSERT INTO order_items(Order_ID, Product_ID, Quantity, Unit_Price) " +
        "VALUES(?,?,?,?)";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getUnitPrice());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // GET ITEMS OF ORDER
    @Override
    public List<OrderItem> getItemsByOrder(int orderId) {

        List<OrderItem> list = new ArrayList<>();

        String sql =
        "SELECT * FROM order_items WHERE Order_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                OrderItem item = new OrderItem(
                        rs.getInt("Order_Item_ID"),
                        rs.getInt("Order_ID"),
                        rs.getInt("Product_ID"),
                        rs.getInt("Quantity"),
                        rs.getDouble("Unit_Price")
                );

                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}