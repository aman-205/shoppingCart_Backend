package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.Dto.OrderDto;
import com.aman.ShoppingCart.model.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrder(Long userId);

    OrderDto convertToDto(Order order);
}
