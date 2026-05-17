package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.Category;
import com.Utilities.DBConnection;
import com.dao.Interfaces.CategoryDAOInterface;

public class CategoryDAO implements CategoryDAOInterface {

    // ✅ ADD CATEGORY
    @Override
    public boolean addCategory(Category c) {

        String sql =
        "INSERT INTO category(Name, Description) VALUES(?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ✅ GET ALL
    @Override
    public List<Category> getAllCategories() {

        List<Category> list = new ArrayList<>();

        String sql = "SELECT * FROM category";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Category c = new Category(
                        rs.getInt("Category_ID"),
                        rs.getString("Name"),
                        rs.getString("Description")
                );

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ GET BY ID
    @Override
    public Category getCategoryById(int id) {

        String sql =
        "SELECT * FROM category WHERE Category_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Category(
                            rs.getInt("Category_ID"),
                            rs.getString("Name"),
                            rs.getString("Description")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ UPDATE
    @Override
    public boolean updateCategory(Category c) {

        String sql =
        "UPDATE category SET Name=?, Description=? WHERE Category_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setInt(3, c.getCategoryId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ✅ DELETE
    @Override
    public boolean deleteCategory(int id) {

        String sql =
        "DELETE FROM category WHERE Category_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}