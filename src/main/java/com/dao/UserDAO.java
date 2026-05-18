package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.model.User;
import com.Utilities.DBConnection;
import com.dao.Interfaces.UserDAOInterface;

public class UserDAO implements UserDAOInterface {

    Connection conn = DBConnection.getConnection();

    // ─────────────────────────────────────────────────────────────────────────
    // EXISTING METHODS (bugs fixed)
    // ─────────────────────────────────────────────────────────────────────────

    // INSERT USER
    @Override
    public boolean addUser(User user) {
        String sql =
            "INSERT INTO users(full_name, email, password, role) VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // GET ALL USERS
    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("full_name"),   // FIX: was "name", column is full_name
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                list.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // GET USER BY ID
    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id=?";
        User user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("full_name"),   // FIX: was "name", column is full_name
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // UPDATE USER
    // FIX: SQL had 4 placeholders but code set 5 params; param order was wrong.
    // Corrected to: full_name=?, email=?, password=?, role=? WHERE id=?
    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET full_name=?, email=?, password=?, role=? WHERE id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getUserId());      // FIX: id goes last (WHERE clause)
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE USER
    @Override
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DASHBOARD METHODS (new)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Login check — returns the matching User or null if credentials are wrong.
     * Used by your LoginServlet.
     */
    public User getUserByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Count of users who registered in the current calendar month.
     * Used for the "New Customers" stat card on the dashboard.
     */
    public int getNewCustomerCount() {
        String sql =
            "SELECT COUNT(*) FROM users " +
            "WHERE role = 'customer' " +
            "  AND YEAR(created_at)  = YEAR(CURRENT_DATE) " +
            "  AND MONTH(created_at) = MONTH(CURRENT_DATE)";
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
     * Count of users who registered in the previous calendar month.
     * Used to compute the "New Customers" % change badge.
     */
    public int getPreviousMonthNewCustomers() {
        String sql =
            "SELECT COUNT(*) FROM users " +
            "WHERE role = 'customer' " +
            "  AND YEAR(created_at)  = YEAR(CURRENT_DATE  - INTERVAL 1 MONTH) " +
            "  AND MONTH(created_at) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)";
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
     * Total number of customers (role = 'customer') in the system.
     * Useful for the Customers admin page.
     */
    public int getTotalCustomerCount() {
        String sql = "SELECT COUNT(*) FROM users WHERE role = 'customer'";
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
     * Returns only customer-role users — used on the admin Customers page.
     */
    public List<User> getAllCustomers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'customer' ORDER BY full_name ASC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}