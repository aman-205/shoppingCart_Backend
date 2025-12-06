package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.model.Cart;
import com.aman.ShoppingCart.model.User;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);



    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
