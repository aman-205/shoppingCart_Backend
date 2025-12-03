package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Repo.CartItemRepo;
import com.aman.ShoppingCart.Repo.CartRepo;
import com.aman.ShoppingCart.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;
    final private AtomicLong cartIdGenerator= new AtomicLong(0);
    @Override
    public Cart getCart(Long id) {
        return cartRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart= getCart(id);
        cartItemRepo.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepo.deleteById(id);


    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }
    @Transactional
    @Override
    public Long initializeNewCart() {
        Cart cart = new Cart();
        // DO NOT set id manually — let DB generate it
        return cartRepo.save(cart).getId();
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepo.findByUserId(userId);
    }

}
