package com.aman.ShoppingCart.Controller;


import com.aman.ShoppingCart.Exception.AlreadyExistException;
import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Respnse.APIResponse;
import com.aman.ShoppingCart.Service.CategoryService;
import com.aman.ShoppingCart.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping("/categories/all")
    public ResponseEntity<APIResponse> getAllCategory(){
        try{

            List<Category> categories= categoryService.getAllCategory();
            return ResponseEntity.ok(new APIResponse("Found",categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Error",INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addCategory(@RequestBody Category name){
        try{

            Category category= categoryService.addCategory(name);
            return ResponseEntity.ok(new APIResponse("Success", category));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<APIResponse> getCategoryById(@PathVariable Long id){
        try{
            Category category= categoryService.getCategoryByID(id);
            return ResponseEntity.ok(new APIResponse("Found",category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<APIResponse> getCategoryByName(@PathVariable String name){
        try{
            Category category= categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new APIResponse("Found",category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long id){
        try{
            Category category= categoryService.getCategoryByID(id);
            return ResponseEntity.ok(new APIResponse("Deleted",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<APIResponse> updateCategory(@PathVariable Long id, @RequestBody Category category){
        try{
            Category updatedcategory= categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new APIResponse("Updated",updatedcategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }
}
