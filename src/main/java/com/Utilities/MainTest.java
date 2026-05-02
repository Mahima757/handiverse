package com.Utilities;

	
	import com.dao.ProductDAO;
	import com.model.Product;


	public class MainTest {

	    public static void main(String[] args) {
	    	 ProductDAO dao = new ProductDAO();
	     // Create a sample product
	        Product p = new Product(
	                1,"Clay Pot",
	                500.0,
	                10,
	                "claypot.jpg",
	                "Handmade traditional clay pot",
	                1   // Category_ID (example: 1 = Pottery)
	        );

	        // Insert product into database
	        boolean result = dao.addProduct(p);

	        if (result) {
	            System.out.println("✅ Product Added Successfully!");
	        } else {
	            System.out.println("❌ Failed to Add Product!");
	        }

	    }
	}

