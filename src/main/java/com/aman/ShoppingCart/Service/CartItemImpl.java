package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Repo.CartItemRepo;
import com.aman.ShoppingCart.Repo.CartRepo;
import com.aman.ShoppingCart.model.Cart;
import com.aman.ShoppingCart.model.CartItem;
import com.aman.ShoppingCart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartItemImpl implements CartItemService{

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartService cartService;

    @Override
    public void addItem(Long cartId, Long productId, int quantity) {
        Cart cart= cartService.getCart(cartId);
        Product product= productService.getProductById(productId);
        CartItem cartItem= cart.getItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if(cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());

        }
        else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepo.save(cartItem);
        cartRepo.save(cart);

    }

    @Override
    public void removeItem(Long cartId, Long productId) {
        Cart cart=cartService.getCart(cartId);
        CartItem itemToRemove=getCartItem(cartId, productId);

        cart.removeItem(itemToRemove);
        cartRepo.save(cart);
    }

    @Override
    public void updateItem(Long cartId, Long productId, int quantity) {
        Cart cart= cartService.getCart(cartId);
        cart.getItems().stream().filter(item->item.getProduct().getId().equals(productId))
                .findFirst().ifPresent(item->{
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount=cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepo.save(cart);

    }
    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart=cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item->item.getProduct().getId()
                        .equals(productId)).findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Item not found"));

    }
}
