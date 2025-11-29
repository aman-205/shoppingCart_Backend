package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Repo.CartItemRepo;
import com.aman.ShoppingCart.Repo.CartRepo;
import com.aman.ShoppingCart.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aman.ShoppingCart.model.CartItem;
import java.math.BigDecimal;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;
    @Override
    public Cart getCart(Long id) {
        Cart cart= cartRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
        BigDecimal total= cart.getTotalAmount();
        cart.setTotalAmount(total);
        return cartRepo.save(cart);
    }

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

}
