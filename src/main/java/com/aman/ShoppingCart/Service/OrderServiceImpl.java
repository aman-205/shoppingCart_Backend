package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.Dto.OrderDto;
import com.aman.ShoppingCart.Enum.OrderStatus;
import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Repo.OrderRepo;
import com.aman.ShoppingCart.Repo.ProductRepo;
import com.aman.ShoppingCart.model.Cart;
import com.aman.ShoppingCart.model.Order;
import com.aman.ShoppingCart.model.OrderItem;
import com.aman.ShoppingCart.model.Product;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart= cartService.getCartByUserId(userId);
        Order order= createOrder(cart);
        List<OrderItem> orderItems= createOrderItems(order,cart);
        order.setOrderItemSet(new HashSet<>(orderItems));
        order.setTotalAmount(totalAmount(orderItems));
        Order savedOrder= orderRepo.save(order);
        cartService.clearCart(cart.getId());


        return savedOrder;
    }

    private BigDecimal totalAmount(List<OrderItem> orderItems){
        return orderItems.stream()
                .map(items->items.getPrice().multiply(new BigDecimal(items.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

    }
    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return  cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepo.save(product);
            return  new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();

    }

    private Order createOrder(Cart cart){
        Order order= new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());

        return order;

    }
    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepo.findById(orderId).map(this::convertToDto)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrder(Long userId){
        List<Order> orders=orderRepo.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();

    }
    @Override
    public OrderDto convertToDto(Order order){
        return modelMapper.map(order,OrderDto.class);
    }
}
