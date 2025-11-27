package com.aman.ShoppingCart.Controller;


import com.aman.ShoppingCart.Request.AddProductRequest;
import com.aman.ShoppingCart.Request.ProductUpdateRequest;
import com.aman.ShoppingCart.Response.APIResponse;
import com.aman.ShoppingCart.Service.ProductService;
import com.aman.ShoppingCart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllProducts(){
        List<Product> productList= productService.getAllProduct();
        return ResponseEntity.ok(new APIResponse("success",productList));
    }


    @GetMapping("product/{id}")
    public ResponseEntity<APIResponse>getProductById(@PathVariable Long id){
        try{
            Product product= productService.getProductById(id);
            return ResponseEntity.ok(new APIResponse("success",product));

        }
        catch (Exception e){

            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequest product){
        try{
            Product product1= productService.addProduct(product);
            return ResponseEntity.ok(new APIResponse("Product  added successfully", product1));
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<APIResponse> updateProduct(@RequestBody ProductUpdateRequest productUpdateRequest, @PathVariable Long id){
        try{
            Product product= productService.getProductById(id);
            return ResponseEntity.ok(new APIResponse("Update product success",product));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<APIResponse> deleteProduct( @PathVariable Long id){
        try{
            productService.deleteProduct(id);
            return ResponseEntity.ok(new APIResponse("Delete product success",id));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<APIResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
        try {
            List<Product> products= productService.getProductByBrandAndName(brandName,productName);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found",null));
            }
            return ResponseEntity.ok(new APIResponse("success",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/by/category-and-brand")
    public ResponseEntity<APIResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        try {
            List<Product> products= productService.getProductByCategoryAndBrand(category,brand);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found",null));
            }
            return ResponseEntity.ok(new APIResponse("success",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(),null));
        }
    }
    @GetMapping("product/{name}")
    public ResponseEntity<APIResponse>getProductByName(@PathVariable String name){
        try{
            List<Product> products= productService.getProductByName(name);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found", null));
            }
            return ResponseEntity.ok(new APIResponse("success",products));
        }
        catch (Exception e){

            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("error",e.getMessage()));
        }
    }
    @GetMapping("product/{brand}")
    public ResponseEntity<APIResponse>getProductByBrand(@PathVariable String brand){
        try{
            List<Product> products= productService.getProductByName(brand);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found", null));
            }
            return ResponseEntity.ok(new APIResponse("success",products));
        }
        catch (Exception e){

            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("error",e.getMessage()));
        }
    }
    @GetMapping("product/{category}")
    public ResponseEntity<APIResponse>getProductByCategory(@PathVariable String category){
        try{
            List<Product> products= productService.getProductByName(category);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not product found", null));
            }
            return ResponseEntity.ok(new APIResponse("success",products));
        }
        catch (Exception e){

            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("error",e.getMessage()));
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<APIResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name){
        try{
            var productCount= productService.countProductByBrandAndName(brand, name);
            return ResponseEntity.ok(new APIResponse("Product count",productCount));

        } catch (Exception e) {
            return ResponseEntity.ok(new APIResponse(e.getMessage(),null));
        }
    }








}
