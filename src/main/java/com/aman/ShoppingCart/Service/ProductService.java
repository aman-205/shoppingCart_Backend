package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.Request.AddProductRequest;
import com.aman.ShoppingCart.Request.ProductUpdateRequest;
import com.aman.ShoppingCart.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(AddProductRequest product);

    List<Product> getAllProduct();

    Product getProductById(long id);

    void deleteProduct(long id);

    Product updateProduct(ProductUpdateRequest product, Long id);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductByBrand(String brand);

    List<Product> getProductByCategoryAndBrand(String category, String brand);

    List<Product> getProductByName(String name);

    List<Product> getProductByBrandAndName(String category, String name);

    Long countProductByBrandAndName(String brand, String name);
}