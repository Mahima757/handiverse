package com.dao.Interfaces;
import java.util.List;
import com.model.CartItem;

public interface CartItemDAOInterface {

    boolean addItem(CartItem item);

    List<CartItem> getItemsByCart(int cartId);

    boolean updateQuantity(int cartItemId, int quantity);

    boolean removeItem(int cartItemId);

    boolean clearCartItems(int cartId);
}
