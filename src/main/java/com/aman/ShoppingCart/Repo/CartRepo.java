package com.aman.ShoppingCart.Repo;

import com.aman.ShoppingCart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
