package com.dao.Interfaces;
import java.util.List;
import com.model.Product;

public interface ProductDAOInterface {

    boolean addProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(int id);

    boolean updateProduct(Product product);

    boolean deleteProduct(int id);

	int getTotalProducts();

	List<Product> searchProducts(String keyword);

	List<Product> getProductsByCategory(int categoryId);
}
