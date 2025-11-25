package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.model.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryByID(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategory();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategory(Long id);



}
