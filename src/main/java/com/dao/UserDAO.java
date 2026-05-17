package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.model.User;
import com.Utilities.DBConnection;
import com.dao.Interfaces.UserDAOInterface;

public class UserDAO implements UserDAOInterface {

    Connection conn = DBConnection.getConnection();

    // INSERT USER (with User object)
    @Override
    public boolean addUser(User user) {

        String sql = "INSERT INTO users(full_name, email, password, role) VALUES(?,?,?,?)";

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

    // ADD USER — called by RegisterServlet
    // Returns: 1 = success | 2 = email already exists | 0 = server error
    public int addUser(String fullName, String hashedPassword, String email) {

        if (emailExists(email)) {
            return 2;
        }

        String sql = "INSERT INTO users(full_name, email, password, role) VALUES(?,?,?,?)";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, hashedPassword);
            ps.setString(4, "customer");    // default role for new registrations

            int rows = ps.executeUpdate();
            return rows > 0 ? 1 : 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
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
                        rs.getString("full_name"),  // BUG WAS: "name"
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
                        rs.getString("full_name"),  // BUG WAS: "name"
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

    // GET USER BY EMAIL — used by LoginServlet
    public User getUserByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email=?";
        User user = null;

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user = new User(
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

        return user;
    }

    // GET USER BY USERNAME
    @Override
    public User getUser(String userName) {

        String sql = "SELECT * FROM users WHERE full_name=?";
        User user = null;

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user = new User(
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

        return user;
    }

    // EMAIL EXISTS CHECK
    public boolean emailExists(String email) {

        String sql = "SELECT COUNT(*) FROM users WHERE email=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // UPDATE USER
    @Override
    public boolean updateUser(User user) {

        String sql = "UPDATE users SET full_name=?, email=?, password=?, role=? WHERE id=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getFullName());  // BUG WAS: setInt(1, userId)
            ps.setString(2, user.getEmail());      // BUG WAS: wrong index
            ps.setString(3, user.getPassword());   // BUG WAS: wrong index
            ps.setString(4, user.getRole());       // BUG WAS: index 5 (out of range)
            ps.setInt(5,    user.getUserId());     // BUG WAS: index 4

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
}