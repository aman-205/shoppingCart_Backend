package com.aman.ShoppingCart.Repo;

import com.aman.ShoppingCart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
