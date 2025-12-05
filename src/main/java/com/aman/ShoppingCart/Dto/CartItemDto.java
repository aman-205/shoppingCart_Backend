package com.aman.ShoppingCart.Dto;

import com.aman.ShoppingCart.model.Product;
import jakarta.persistence.Id;

import java.math.BigDecimal;

public class CartItemDto {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;

    private ProductDto product;

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
