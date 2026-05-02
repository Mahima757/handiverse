package com.dao;
import java.sql.*;
import com.Utilities.DBConnection;
import com.dao.Interfaces.CartDAOInterface;
import com.model.Cart;

public class CartDAO implements CartDAOInterface {

    Connection conn = DBConnection.getConnection();

    // CREATE CART
    @Override
    public int createCart(int userId) {

        int cartId = -1;

        String sql = "INSERT INTO cart(User_ID) VALUES(?)";

        try {

            PreparedStatement ps =
                    conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, userId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next())
                cartId = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cartId;
    }

    // GET CART BY USER
    @Override
    public Cart getCartByUserId(int userId) {

        Cart cart = null;

        String sql = "SELECT * FROM cart WHERE User_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                cart = new Cart(
                        rs.getInt("Cart_ID"),
                        rs.getInt("User_ID"),
                        rs.getTimestamp("Created_At")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cart;
    }

    // DELETE CART
    @Override
    public boolean deleteCart(int cartId) {

        String sql = "DELETE FROM cart WHERE Cart_ID=?";

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