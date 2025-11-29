package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.Dto.ImageDto;
import com.aman.ShoppingCart.Dto.ProductDto;
import com.aman.ShoppingCart.Exception.ProductNotFoundException;
import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Repo.CategoryRepo;
import com.aman.ShoppingCart.Repo.ImageRepo;
import com.aman.ShoppingCart.Repo.ProductRepo;
import com.aman.ShoppingCart.Request.AddProductRequest;
import com.aman.ShoppingCart.Request.ProductUpdateRequest;
import com.aman.ShoppingCart.model.Category;
import com.aman.ShoppingCart.model.Image;
import com.aman.ShoppingCart.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImageRepo imageRepo;
    @Override
    public Product addProduct(AddProductRequest request) {
        boolean exists = productRepo.existsByNameAndBrand(request.getName(), request.getBrand());
        if (exists) {
            throw new RuntimeException("Product '" + request.getName() + "' already exists for brand '" + request.getBrand() + "'");
        }

        Category category= Optional.ofNullable(categoryRepo.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                        Category newCategory= new Category(request.getCategory().getName());
                        return categoryRepo.save(newCategory);
                })
                ;
        Product product = createProduct(request, category);
        return productRepo.save(product);
    }
    public Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category
        );
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(long id) {
        return productRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public void deleteProduct(long id) {
        productRepo.findById(id).ifPresentOrElse(productRepo::delete,()->{throw new ProductNotFoundException("Product not found");});

    }

    @Override
    public Product updateProduct(ProductUpdateRequest product, Long id) {
        return  productRepo.findById(id)
                .map(exProduct-> updateExstingProduct(exProduct,product))
                .map(productRepo::save)
                .orElseThrow(()-> new ProductNotFoundException("Product Not found"));
    }
    private Product updateExstingProduct(Product ex, ProductUpdateRequest request){

        ex.setName(request.getName());
        ex.setBrand(request.getBrand());
        ex.setPrice(request.getPrice());
        ex.setDescription(request.getDescription());
        ex.setInventory(request.getInventory());
        Category category=categoryRepo.findByName(request.getCategory().getName());
        ex.setCategory(category);
        return ex;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepo.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepo.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepo.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepo.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepo.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto= modelMapper.map(product,ProductDto.class);
        List<Image> images = imageRepo.findByProductId(product.getId());
        List<ImageDto> imageDtos=images.stream()
                .map(image->modelMapper.map(image,ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
