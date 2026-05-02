package com.model;

public class OrderItem {
	 private int     orderItemId;
	    private int     orderId;
	    private int     productId;
	    private int     quantity;
	    private double  unitPrice;
	    private Product product;    // Joined product — not a DB column
	 
	    // ─── Constructors ─────────────────────────────────────────────────────────
	 
	    public OrderItem() {}
	 
	    public OrderItem(int orderItemId, int orderId, int productId,
	                     int quantity, double unitPrice) {
	        this.orderItemId = orderItemId;
	        this.orderId     = orderId;
	        this.productId   = productId;
	        this.quantity    = quantity;
	        this.unitPrice   = unitPrice;
	    }
	 
	    // ─── Getters & Setters ────────────────────────────────────────────────────
	 
	    public int getOrderItemId() {
	        return orderItemId;
	    }
	 
	    public void setOrderItemId(int orderItemId) {
	        this.orderItemId = orderItemId;
	    }
	 
	    public int getOrderId() {
	        return orderId;
	    }
	 
	    public void setOrderId(int orderId) {
	        this.orderId = orderId;
	    }
	 
	    public int getProductId() {
	        return productId;
	    }
	 
	    public void setProductId(int productId) {
	        this.productId = productId;
	    }
	 
	    public int getQuantity() {
	        return quantity;
	    }
	 
	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }
	 
	    public double getUnitPrice() {
	        return unitPrice;
	    }
	 
	    public void setUnitPrice(double unitPrice) {
	        this.unitPrice = unitPrice;
	    }
	 
	    public Product getProduct() {
	        return product;
	    }
	 
	    public void setProduct(Product product) {
	        this.product = product;
	    }
	 
	    // ─── Convenience Helpers ─────────────────────────────────────────────────
	 
	    /**
	     * Returns unit price × quantity for this line item.
	     */
	    public double getSubtotal() {
	        return unitPrice * quantity;
	    }
	 
	    @Override
	    public String toString() {
	        return "OrderItem{orderItemId=" + orderItemId
	             + ", productId=" + productId
	             + ", quantity="  + quantity
	             + ", unitPrice=" + unitPrice + "}";
	    }
}
