package com.aman.ShoppingCart.Controller;

import com.aman.ShoppingCart.Dto.OrderDto;
import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Response.APIResponse;
import com.aman.ShoppingCart.Service.OrderService;
import com.aman.ShoppingCart.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/orders/")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("placeOrder/{userId}")
    public ResponseEntity<APIResponse> createOrder(@PathVariable  Long userId){
        try{
            Order order= orderService.placeOrder(userId);
            OrderDto orderDto=orderService.convertToDto(order);
            return ResponseEntity.ok(new APIResponse("Success",orderDto));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }
    @GetMapping("{id}")
    public ResponseEntity<APIResponse> getOrder(@PathVariable Long id){
        try{
            OrderDto order= orderService.getOrder(id);
            return ResponseEntity.ok(new APIResponse("Success",order));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(),null));
        }
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<APIResponse> getOrderByUser(@PathVariable Long userId){
        try{
            List<OrderDto> orders= orderService.getUserOrder(userId);
            return ResponseEntity.ok(new APIResponse("Success",orders));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(),null));
        }
    }
}
