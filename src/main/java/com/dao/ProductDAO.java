package com.dao;

import com.Utilities.DBConnection;
import com.model.Product;
import com.model.Category;

import java.sql.*;
import java.util.*;

public class ProductDAO {

    // ── GET ALL PRODUCTS ──────────────────────────────────────────────────────
    public List<Product> getAllProducts() {

        List<Product> list = new ArrayList<>();

        // BUG WAS: table name typo + wrong column case
        String sql =
            "SELECT p.*, c.Name AS categoryName " +
            "FROM product p " +                               // ✅ "product" not "products"
            "JOIN category c ON p.Category_ID = c.Category_ID " +
            "ORDER BY p.Product_ID DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ── GET FILTERED PRODUCTS ─────────────────────────────────────────────────
    public List<Product> getFilteredProducts(
            String keyword,
            Set<Integer> categories,
            double maxPrice,
            String sort) {

        List<Product> list = new ArrayList<>();

        // BUG WAS: table was "products" (with s) — actual table is "product"
        StringBuilder sql = new StringBuilder(
            "SELECT p.*, c.Name AS categoryName " +
            "FROM product p " +                               // ✅ fixed table name
            "JOIN category c ON p.Category_ID = c.Category_ID " +
            "WHERE p.Price <= ? "
        );

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND p.Product_Name LIKE ? ");         // ✅ correct column name
        }

        if (categories != null && !categories.isEmpty()) {
            sql.append("AND p.Category_ID IN (");
            sql.append(String.join(",", Collections.nCopies(categories.size(), "?")));
            sql.append(") ");
        }

        // Sorting
        switch (sort != null ? sort : "") {
            case "price_asc":  sql.append("ORDER BY p.Price ASC");          break;
            case "price_desc": sql.append("ORDER BY p.Price DESC");         break;
            case "name_asc":   sql.append("ORDER BY p.Product_Name ASC");   break;
            default:           sql.append("ORDER BY p.Product_ID DESC");     break;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;

            ps.setDouble(index++, maxPrice);

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(index++, "%" + keyword.trim() + "%");
            }

            if (categories != null && !categories.isEmpty()) {
                for (Integer id : categories) {
                    ps.setInt(index++, id);
                }
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ── GET PRODUCT BY ID ─────────────────────────────────────────────────────
    public Product getProductById(int id) {

        String sql =
            "SELECT p.*, c.Name AS categoryName " +
            "FROM product p " +
            "JOIN category c ON p.Category_ID = c.Category_ID " +
            "WHERE p.Product_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ── GET ALL CATEGORIES (used by ShopServlet) ──────────────────────────────
    public List<Category> getAllCategories() {

        List<Category> categories = new ArrayList<>();

        String sql = "SELECT * FROM category ORDER BY Name ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(
                    rs.getInt("Category_ID"),     // ✅ exact DB column name
                    rs.getString("Name"),          // ✅ exact DB column name
                    rs.getString("Description")    // ✅ exact DB column name
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    // ── COUNT (for pagination) ────────────────────────────────────────────────
    public int countFilteredProducts(
            String keyword,
            Set<Integer> categories,
            double maxPrice) {

        StringBuilder sql = new StringBuilder(
            "SELECT COUNT(*) FROM product p WHERE p.Price <= ? "
        );

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND p.Product_Name LIKE ? ");
        }

        if (categories != null && !categories.isEmpty()) {
            sql.append("AND p.Category_ID IN (");
            sql.append(String.join(",", Collections.nCopies(categories.size(), "?")));
            sql.append(") ");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            ps.setDouble(index++, maxPrice);

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(index++, "%" + keyword.trim() + "%");
            }

            if (categories != null && !categories.isEmpty()) {
                for (Integer id : categories) ps.setInt(index++, id);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ── ADD PRODUCT ───────────────────────────────────────────────────────────
    public boolean addProduct(Product p) {

        String sql =
            "INSERT INTO product(Product_Name, Price, Quantity, Image, Description, Category_ID) " +
            "VALUES(?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getProductName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getQuantity());
            ps.setString(4, p.getImage());
            ps.setString(5, p.getDescription());
            ps.setInt(6, p.getCategoryId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ── UPDATE PRODUCT ────────────────────────────────────────────────────────
    public boolean updateProduct(Product p) {

        String sql =
            "UPDATE product SET Product_Name=?, Price=?, Quantity=?, " +
            "Image=?, Description=?, Category_ID=? WHERE Product_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getProductName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getQuantity());
            ps.setString(4, p.getImage());
            ps.setString(5, p.getDescription());
            ps.setInt(6, p.getCategoryId());
            ps.setInt(7, p.getProductId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ── DELETE PRODUCT ────────────────────────────────────────────────────────
    public boolean deleteProduct(int id) {

        String sql = "DELETE FROM product WHERE Product_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ── PRIVATE ROW MAPPER ────────────────────────────────────────────────────
    // Single place to map ResultSet → Product, used by all methods above
    private Product mapRow(ResultSet rs) throws SQLException {

        Product p = new Product(
            rs.getInt("Product_ID"),        // ✅ matches DB column
            rs.getString("Product_Name"),   // ✅ matches DB column
            rs.getDouble("Price"),          // ✅ matches DB column
            rs.getInt("Quantity"),          // ✅ matches DB column
            rs.getString("Image"),          // ✅ matches DB column
            rs.getString("Description"),    // ✅ matches DB column
            rs.getInt("Category_ID")        // ✅ matches DB column
        );

        p.setCategoryName(rs.getString("categoryName"));

        return p;
    }
}