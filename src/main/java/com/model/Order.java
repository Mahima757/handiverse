package com.model;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {

    // ===== Database Columns =====
    private int orderId;
    private int userId;
    private Timestamp orderDate;
    private double totalAmount;
    private String status;

    // ===== Extra Fields (NOT DB columns) =====
    private String customerName;       // From JOIN users table
    private List<OrderItem> orderItems;

    // ===== Constructors =====

    public Order() {
        this.orderItems = new ArrayList<>();
    }

    // Used when reading from database
    public Order(int orderId, int userId, Timestamp orderDate,
                 double totalAmount, String status) {

        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderItems = new ArrayList<>();
    }

    // Used when creating new order
    public Order(int userId, double totalAmount, String status) {

        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderItems = new ArrayList<>();
    }

    // ===== Getters & Setters =====

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // ===== Helper Methods =====

    public void addItem(OrderItem item) {
        this.orderItems.add(item);
    }

    public int getTotalItems() {
        int total = 0;
        for (OrderItem item : orderItems) {
            total += item.getQuantity();
        }
        return total;
    }

    // ===== Debug =====

    @Override
    public String toString() {
        return "Order { orderId=" + orderId +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", status=" + status + " }";
    }
}