package com.model;

public class Product {
	    private int    productId;
	    private String productName;
	    private double price;
	    private String image;           // File name stored in /images/products/
	    private int    quantity;
	    private String description;
	    private int    categoryId;
	    private String categoryName;    // Joined from Category table (not a DB column)
	 
	    // ─── Constructors ─────────────────────────────────────────────────────────
	 
	 
	    public Product(int productId, String productName, double price,
	                   int quantity, String image, String description, int categoryId) {
	        this.productId   = productId;
	        this.productName = productName;
	        this.price       = price;
	        this.image       = image;
	        this.quantity    = quantity;
	        this.description = description;
	        this.categoryId  = categoryId;
	    }
	 
	    // ─── Getters & Setters ────────────────────────────────────────────────────
	 
	    public int getProductId() {
	        return productId;
	    }
	 
	    public void setProductId(int productId) {
	        this.productId = productId;
	    }
	 
	    public String getProductName() {
	        return productName;
	    }
	 
	    public void setProductName(String productName) {
	        this.productName = productName;
	    }
	 
	    public double getPrice() {
	        return price;
	    }
	 
	    public void setPrice(double price) {
	        this.price = price;
	    }
	 
	    public String getImage() {
	        return image;
	    }
	 
	    public void setImage(String image) {
	        this.image = image;
	    }
	 
	    public int getQuantity() {
	        return quantity;
	    }
	 
	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }
	 
	    public String getDescription() {
	        return description;
	    }
	 
	    public void setDescription(String description) {
	        this.description = description;
	    }
	 
	    public int getCategoryId() {
	        return categoryId;
	    }
	 
	    public void setCategoryId(int categoryId) {
	        this.categoryId = categoryId;
	    }
	 
	    public String getCategoryName() {
	        return categoryName;
	    }
	 
	    public void setCategoryName(String categoryName) {
	        this.categoryName = categoryName;
	    }
	 
	    // ─── Convenience Helpers ─────────────────────────────────────────────────
	 
	    /**
	     * Returns true if this product is currently in stock.
	     */
	    public boolean isInStock() {
	        return quantity > 0;
	    }
	 
	    /**
	     * Returns the image path for use in JSP src attributes.
	     * Falls back to a placeholder if no image is set.
	     */
	    public String getImagePath() {
	        return (image != null && !image.isEmpty())
	               ? "images/products/" + image
	               : "images/placeholder.png";
	    }
	 
	    @Override
	    public String toString() {
	        return "Product{productId=" + productId
	             + ", productName='" + productName + "'"
	             + ", price="        + price
	             + ", quantity="     + quantity
	             + ", categoryId="   + categoryId + "}";
	    }
}
