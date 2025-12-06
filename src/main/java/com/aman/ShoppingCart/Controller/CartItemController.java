package com.aman.ShoppingCart.Controller;

import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Repo.UserRepo;
import com.aman.ShoppingCart.Response.APIResponse;
import com.aman.ShoppingCart.Service.CartItemService;
import com.aman.ShoppingCart.Service.CartService;
import com.aman.ShoppingCart.Service.UserService;
import com.aman.ShoppingCart.model.Cart;
import com.aman.ShoppingCart.model.User;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("${api.prefix}/cartItems/")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;
    @PostMapping("add")
    public ResponseEntity<APIResponse> addItem( @RequestParam Long productId, @RequestParam Integer quantity){
       try{
           User user=userService.getAuthenticatedUser();
           Cart cart=cartService.initializeNewCart(user);

           cartItemService.addItem(cart.getId(),productId,quantity);
           return ResponseEntity.ok(new APIResponse("Items Added",null));

       } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
       }
       catch (JwtException e){
           return ResponseEntity.status(UNAUTHORIZED).body(new APIResponse(e.getMessage(),null));
       }
    }

    @DeleteMapping("remove/{cartId}/{productId}")
    public ResponseEntity<APIResponse> remove(@PathVariable Long cartId, @PathVariable Long productId){
        try{
            cartItemService.removeItem(cartId,productId);
            return ResponseEntity.ok(new APIResponse("Item Removed",null));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }

    @PutMapping("update/{cartId}/{productId}/{quantity}")
    public ResponseEntity<APIResponse> update(@PathVariable Long cartId, @PathVariable Long productId,@PathVariable int quantity){
        try{
            cartItemService.updateItem(cartId, productId, quantity);
            return ResponseEntity.ok(new APIResponse("Updated",null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Cart not found",null));
        }
    }



}
