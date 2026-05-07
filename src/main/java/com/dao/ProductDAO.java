package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.model.Product;
import com.Utilities.DBConnection;
import com.dao.Interfaces.ProductDAOInterface;

public class ProductDAO implements ProductDAOInterface {

    Connection conn = DBConnection.getConnection();

    // ADD PRODUCT
    @Override
    public boolean addProduct(Product p) {

        String sql = "INSERT INTO product(Product_Name, Price, Quantity, Image, Description, Category_ID) VALUES(?,?,?,?,?,?)";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, p.getProductName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getQuantity());
            ps.setString(4, p.getImage());
            ps.setString(5, p.getDescription());
            ps.setInt(6, p.getCategoryId());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // GET ALL PRODUCTS
    @Override
    public List<Product> getAllProducts() {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT p.*, c.Name AS Category_Name FROM product p " +
                     "JOIN category c ON p.Category_ID = c.Category_ID " +
                     "ORDER BY p.Product_ID DESC";

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // GET PRODUCT BY ID
    @Override
    public Product getProductById(int id) {

        String sql = "SELECT p.*, c.Name AS Category_Name FROM product p " +
                     "JOIN category c ON p.Category_ID = c.Category_ID " +
                     "WHERE p.Product_ID=?";
        Product p = null;

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = mapRow(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }

    // GET PRODUCTS BY CATEGORY
    @Override
    public List<Product> getProductsByCategory(int categoryId) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT p.*, c.Name AS Category_Name FROM product p " +
                     "JOIN category c ON p.Category_ID = c.Category_ID " +
                     "WHERE p.Category_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // SEARCH PRODUCTS
    @Override
    public List<Product> searchProducts(String keyword) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT p.*, c.Name AS Category_Name FROM product p " +
                     "JOIN category c ON p.Category_ID = c.Category_ID " +
                     "WHERE p.Product_Name LIKE ? OR p.Description LIKE ? OR c.Name LIKE ?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            String like = "%" + keyword + "%";

            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // SEARCH IN CATEGORY
    public List<Product> searchProductsInCategory(String keyword, int categoryId) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT p.*, c.Name AS Category_Name FROM product p " +
                     "JOIN category c ON p.Category_ID = c.Category_ID " +
                     "WHERE (p.Product_Name LIKE ? OR p.Description LIKE ?) " +
                     "AND p.Category_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            String like = "%" + keyword + "%";

            ps.setString(1, like);
            ps.setString(2, like);
            ps.setInt(3, categoryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ── FILTERED PRODUCTS with category[], price range, sort, pagination ──
    // Used by ShopServlet for the full shop page filter form
    public List<Product> getFilteredProducts(int[] categoryIds,
                                              double minPrice, double maxPrice,
                                              String sort,
                                              int offset, int limit) {

        List<Product> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT p.*, c.Name AS Category_Name FROM product p " +
            "JOIN category c ON p.Category_ID = c.Category_ID " +
            "WHERE p.Price >= ? AND p.Price <= ? "
        );

        // Add category IN clause if specific categories selected
        if (categoryIds != null && categoryIds.length > 0) {
            sql.append("AND p.Category_ID IN (");
            for (int i = 0; i < categoryIds.length; i++) {
                sql.append(i == 0 ? "?" : ",?");
            }
            sql.append(") ");
        }

        // Sort
        switch (sort != null ? sort : "") {
            case "price_asc":  sql.append("ORDER BY p.Price ASC ");   break;
            case "price_desc": sql.append("ORDER BY p.Price DESC ");  break;
            case "name_asc":   sql.append("ORDER BY p.Product_Name ASC "); break;
            default:           sql.append("ORDER BY p.Product_ID DESC "); break; // newest
        }

        sql.append("LIMIT ? OFFSET ?");

        try {

            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int idx = 1;

            ps.setDouble(idx++, minPrice);
            ps.setDouble(idx++, maxPrice);

            if (categoryIds != null) {
                for (int catId : categoryIds) {
                    ps.setInt(idx++, catId);
                }
            }

            ps.setInt(idx++, limit);
            ps.setInt(idx,   offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // COUNT for pagination
    public int getFilteredProductCount(int[] categoryIds,
                                        double minPrice, double maxPrice) {

        StringBuilder sql = new StringBuilder(
            "SELECT COUNT(*) FROM product p " +
            "WHERE p.Price >= ? AND p.Price <= ? "
        );

        if (categoryIds != null && categoryIds.length > 0) {
            sql.append("AND p.Category_ID IN (");
            for (int i = 0; i < categoryIds.length; i++) {
                sql.append(i == 0 ? "?" : ",?");
            }
            sql.append(") ");
        }

        try {

            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int idx = 1;

            ps.setDouble(idx++, minPrice);
            ps.setDouble(idx++, maxPrice);

            if (categoryIds != null) {
                for (int catId : categoryIds) {
                    ps.setInt(idx++, catId);
                }
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // UPDATE PRODUCT
    @Override
    public boolean updateProduct(Product p) {

        String sql = "UPDATE product SET Product_Name=?, Price=?, Quantity=?, " +
                     "Image=?, Description=?, Category_ID=? WHERE Product_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, p.getProductName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getQuantity());
            ps.setString(4, p.getImage());
            ps.setString(5, p.getDescription());
            ps.setInt(6, p.getCategoryId());
            ps.setInt(7, p.getProductId());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // DELETE PRODUCT
    @Override
    public boolean deleteProduct(int id) {

        String sql = "DELETE FROM product WHERE Product_ID=?";

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

    // GET TOTAL PRODUCTS
    @Override
    public int getTotalProducts() {

        String sql = "SELECT COUNT(*) FROM product";

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ── Private row mapper ────────────────────────────────────
    private Product mapRow(ResultSet rs) throws SQLException {

        Product p = new Product(
                rs.getInt("Product_ID"),
                rs.getString("Product_Name"),
                rs.getDouble("Price"),
                rs.getInt("Quantity"),
                rs.getString("Image"),
                rs.getString("Description"),
                rs.getInt("Category_ID")
        );
        p.setCategoryName(rs.getString("Category_Name"));
        return p;
    }
}