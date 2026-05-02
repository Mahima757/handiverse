package com.dao.Interfaces;
import java.util.List;
import com.model.OrderItem;

public interface OrderItemDAOInterface {

    boolean addOrderItem(OrderItem item);

    List<OrderItem> getItemsByOrder(int orderId);
}