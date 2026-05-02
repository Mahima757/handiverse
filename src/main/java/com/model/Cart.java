package com.model;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
public class Cart {
	   private int       cartId;
	    private int       userId;
	    private Timestamp createdAt;
	    private List<CartItem> items;   // Not a DB column — populated by CartDAO
	 
	    // ─── Constructors ─────────────────────────────────────────────────────────
	 
	    public Cart() {
	        this.items = new ArrayList<>();
	    }
	 
	    public Cart(int cartId, int userId, Timestamp createdAt) {
	        this.cartId    = cartId;
	        this.userId    = userId;
	        this.createdAt = createdAt;
	        this.items     = new ArrayList<>();
	    }
	 
	    // ─── Getters & Setters ────────────────────────────────────────────────────
	 
	    public int getCartId() {
	        return cartId;
	    }
	 
	    public void setCartId(int cartId) {
	        this.cartId = cartId;
	    }
	 
	    public int getUserId() {
	        return userId;
	    }
	 
	    public void setUserId(int userId) {
	        this.userId = userId;
	    }
	 
	    public Timestamp getCreatedAt() {
	        return createdAt;
	    }
	 
	    public void setCreatedAt(Timestamp createdAt) {
	        this.createdAt = createdAt;
	    }
	 
	    public List<CartItem> getItems() {
	        return items;
	    }
	 
	    public void setItems(List<CartItem> items) {
	        this.items = items;
	    }
	 
	    // ─── Convenience Helpers ─────────────────────────────────────────────────
	 
	    /**
	     * Calculates the total price of all items in this cart.
	     */
	    public double getTotalPrice() {
	        double total = 0;
	        for (CartItem item : items) {
	            total += item.getSubtotal();
	        }
	        return total;
	    }
	 
	    /**
	     * Returns the total number of individual items in the cart.
	     */
	    public int getTotalItems() {
	        int count = 0;
	        for (CartItem item : items) {
	            count += item.getQuantity();
	        }
	        return count;
	    }
	 
	    @Override
	    public String toString() {
	        return "Cart{cartId=" + cartId + ", userId=" + userId
	             + ", items=" + items.size() + "}";
	    }
}
