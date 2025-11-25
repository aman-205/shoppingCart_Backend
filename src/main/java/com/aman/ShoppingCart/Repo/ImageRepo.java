package com.aman.ShoppingCart.Repo;

import com.aman.ShoppingCart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Long> {
}
