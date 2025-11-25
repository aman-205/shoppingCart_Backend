package com.aman.ShoppingCart.Service;

import com.aman.ShoppingCart.Dto.ImageDto;
import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Repo.ImageRepo;
import com.aman.ShoppingCart.model.Image;
import com.aman.ShoppingCart.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepo imageRepo;
    @Autowired
    private ProductService productService;


    @Override
    public Image getImageById(Long id) {
        return imageRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Image not found!"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepo.findById(id).ifPresentOrElse(imageRepo::delete,()->{
            throw new ResourceNotFoundException("Image not found");
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product= productService.getProductById(productId);
        List<ImageDto> savedImageDto= new ArrayList<>();
        for( MultipartFile file:files){
            try {
                Image image= new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String url="/api/v1/images/image/download/";
                String dwnldUrl= url+image.getId();
                image.setDownloadUrl(dwnldUrl);
                Image savedImage=imageRepo.save(image);
                savedImage.setDownloadUrl(url + savedImage.getId());
                imageRepo.save(savedImage);

                ImageDto imageDto= new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDwnldUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);

            }
            catch (IOException | SQLException e){
                throw  new RuntimeException(e.getMessage());

            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long id) {
        Image image= getImageById(id);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepo.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
