package com.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.Category;
import com.Utilities.DBConnection;
import com.dao.Interfaces.CategoryDAOInterface;

public class CategoryDAO implements CategoryDAOInterface {

    Connection conn = DBConnection.getConnection();

    // ADD CATEGORY
    @Override
    public boolean addCategory(Category c) {

        String sql = "INSERT INTO category(Name, Description) VALUES(?,?)";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // GET ALL CATEGORIES
    @Override
    public List<Category> getAllCategories() {

        List<Category> list = new ArrayList<>();

        String sql = "SELECT * FROM category";

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

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

    // GET BY ID
    @Override
    public Category getCategoryById(int id) {

        String sql = "SELECT * FROM category WHERE Category_ID=?";
        Category c = null;

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                c = new Category(
                        rs.getInt("Category_ID"),
                        rs.getString("Name"),
                        rs.getString("Description")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }

    // UPDATE
    @Override
    public boolean updateCategory(Category c) {

        String sql =
        "UPDATE category SET Name=?, Description=? WHERE Category_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setInt(3, c.getCategoryId());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // DELETE
    @Override
    public boolean deleteCategory(int id) {

        String sql = "DELETE FROM category WHERE Category_ID=?";

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