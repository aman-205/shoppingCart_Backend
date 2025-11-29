package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

}
