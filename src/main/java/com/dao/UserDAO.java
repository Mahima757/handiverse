package com.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.model.User;
import com.Utilities.DBConnection;
import com.dao.Interfaces.UserDAOInterface;

public class UserDAO implements UserDAOInterface {

	 Connection conn = DBConnection.getConnection();

	    // INSERT USER
	    @Override
	    public boolean addUser(User user) {

	        String sql =
	            "INSERT INTO users(full_name,email,password,role) VALUES(?,?,?,?)";

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
	                        rs.getString("full_name"),
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
	                        rs.getString("name"),
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
	    @Override
	    public boolean updateUser(User user) {

	        String sql = "UPDATE users SET name=?, email=?, password=? WHERE id=?";

	        try {

	            PreparedStatement ps = conn.prepareStatement(sql);
                
	            ps.setInt(1, user.getUserId());
	            ps.setString(2, user.getFullName());
	            ps.setString(3, user.getEmail());
	            ps.setString(4, user.getPassword());
                ps.setString(5, user.getRole());
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