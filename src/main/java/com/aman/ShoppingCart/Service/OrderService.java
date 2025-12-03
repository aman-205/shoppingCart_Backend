package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.model.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrder(Long userId);
}
