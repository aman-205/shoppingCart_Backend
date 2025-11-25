package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.Exception.AlreadyExistException;
import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Repo.CategoryRepo;
import com.aman.ShoppingCart.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public Category getCategoryByID(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category Not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.ofNullable(category).filter(c->!categoryRepo.existsByName(c.getName()))
                .map(categoryRepo::save).orElseThrow(()-> new AlreadyExistException(category.getName()+"already exist"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryByID(id))
                .map(oldCat->{
                    oldCat.setName(category.getName());
                    return categoryRepo.save(oldCat);
                }).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete, ()->{
            throw  new ResourceNotFoundException("Category not found");
        });
    }
}
