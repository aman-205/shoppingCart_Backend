package com.aman.ShoppingCart.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private  Cart cart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.price = totalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public void setTotalPrice() {
        this.price = this.unitPrice.multiply(new BigDecimal(quantity));
    }

}

