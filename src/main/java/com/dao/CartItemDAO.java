package com.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.Utilities.DBConnection;
import com.dao.Interfaces.CartItemDAOInterface;
import com.model.CartItem;

public class CartItemDAO implements CartItemDAOInterface {

    Connection conn = DBConnection.getConnection();

    // ADD ITEM
    @Override
    public boolean addItem(CartItem item) {

        String sql =
        "INSERT INTO cart_item(Cart_ID, Product_ID, Quantity) VALUES(?,?,?)";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, item.getCartId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getQuantity());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // GET ITEMS
    @Override
    public List<CartItem> getItemsByCart(int cartId) {

        List<CartItem> list = new ArrayList<>();

        String sql = "SELECT * FROM cart_item WHERE Cart_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                CartItem item = new CartItem();

                item.setCartItemId(rs.getInt("CartItem_ID"));
                item.setCartId(rs.getInt("Cart_ID"));
                item.setProductId(rs.getInt("Product_ID"));
                item.setQuantity(rs.getInt("Quantity"));

                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE QUANTITY
    @Override
    public boolean updateQuantity(int cartItemId, int quantity) {

        String sql =
        "UPDATE cart_item SET Quantity=? WHERE CartItem_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // REMOVE ITEM
    @Override
    public boolean removeItem(int cartItemId) {

        String sql = "DELETE FROM cart_item WHERE CartItem_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartItemId);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // CLEAR CART
    @Override
    public boolean clearCartItems(int cartId) {

        String sql = "DELETE FROM cart_item WHERE Cart_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartId);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}