package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.Dto.ImageDto;
import com.aman.ShoppingCart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long id);
}
