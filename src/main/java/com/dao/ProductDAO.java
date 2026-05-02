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

        String sql =
        "INSERT INTO product(Product_Name,Price,Quantity,Image,Description,Category_ID) VALUES(?,?,?,?,?,?)";

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

        String sql = "SELECT * FROM product";

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Product p = new Product(
                		rs.getInt("Product_ID"),
                        rs.getString("Product_Name"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getInt("Category_ID")
                );

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // GET PRODUCT BY ID
    @Override
    public Product getProductById(int id) {

        String sql = "SELECT * FROM product WHERE Product_ID=?";
        Product p = null;

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                p = new Product(
                		rs.getInt("Product_ID"),
                        rs.getString("Product_Name"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getInt("Category_ID")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }
   //Get product by category
    public List<Product> getProductsByCategory(int categoryId) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM product WHERE Category_ID=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Product p = new Product(
                        rs.getInt("Product_ID"),
                        rs.getString("Product_Name"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getInt("Category_ID")
                );

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    // UPDATE PRODUCT
    @Override
    public boolean updateProduct(Product p) {

        String sql =
        "UPDATE product SET Product_Name=?, Price=?, Quantity=?, Image=?, Description=?, Category_ID=? WHERE Product_ID=?";

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
}