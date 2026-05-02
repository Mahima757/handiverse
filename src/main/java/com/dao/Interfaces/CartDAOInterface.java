package com.dao.Interfaces;
import com.model.Cart;

public interface CartDAOInterface {

    int createCart(int userId);

    Cart getCartByUserId(int userId);

    boolean deleteCart(int cartId);
}
