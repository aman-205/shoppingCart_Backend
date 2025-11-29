package com.aman.ShoppingCart.Repo;

import com.aman.ShoppingCart.Service.CartItemService;
import com.aman.ShoppingCart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}
