package com.dao.Interfaces;
import java.util.List;
import com.model.Order;

public interface OrderDAOInterface {

    int createOrder(Order order);

    Order getOrderById(int orderId);

    List<Order> getOrdersByUser(int userId);

    List<Order> getAllOrders();

    boolean updateOrderStatus(int orderId, String status);
}