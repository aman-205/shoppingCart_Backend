package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.model.CartItem;

public interface CartItemService {
    void addItem(Long cartId, Long productId, int quantity);
    void removeItem(Long cartId, Long productId);
    void updateItem(Long cartId, Long productId, int quantity);


    CartItem getCartItem(Long cartId, Long productId);
}
