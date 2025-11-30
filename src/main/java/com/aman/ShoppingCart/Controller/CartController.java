package com.aman.ShoppingCart.Controller;

import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Repo.CartItemRepo;
import com.aman.ShoppingCart.Repo.CartRepo;
import com.aman.ShoppingCart.Response.APIResponse;
import com.aman.ShoppingCart.Service.CartItemService;
import com.aman.ShoppingCart.Service.CartService;
import com.aman.ShoppingCart.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("${api.prefix}/carts/")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepo cartRepo;

    @GetMapping("getCart/{id}")
    public ResponseEntity<APIResponse> getCart(@PathVariable Long id){
        try{
            Cart cart=cartService.getCart(id);
            return ResponseEntity.ok(new APIResponse("Success", cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<APIResponse> clearCart(@PathVariable Long id){
        try{
            cartService.clearCart(id);
            return ResponseEntity.ok(new APIResponse("Clear cart Success",null));

        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Resource not found",null));
        }
    }

    @GetMapping("total/{id}")
    public ResponseEntity<APIResponse> getTotalAmount(@PathVariable Long id){
        try{
            BigDecimal totalPrice= cartService.getTotalPrice(id);
            return ResponseEntity.ok(new APIResponse("Total Price",totalPrice));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Resource not found",null));
        }
    }

}
