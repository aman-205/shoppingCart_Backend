package com.aman.ShoppingCart.Controller;

import com.aman.ShoppingCart.Dto.ImageDto;
import com.aman.ShoppingCart.Exception.ResourceNotFoundException;
import com.aman.ShoppingCart.Respnse.APIResponse;
import com.aman.ShoppingCart.Service.ImageService;
import com.aman.ShoppingCart.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<APIResponse> saveImage(@RequestParam List<MultipartFile> files, @RequestParam  Long productId){
        try{
            List<ImageDto> imageDtos= imageService.saveImage(files, productId);
            return ResponseEntity.ok(new APIResponse("Upload success", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Upload failed!",e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId")
    public ResponseEntity<Resource> dwnldImage(@PathVariable Long imageId) throws SQLException {
        Image image= imageService.getImageById(imageId);
        ByteArrayResource resource= new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" +image.getFileName()+"\"").body(resource);

    }
    @PutMapping("/image/{imageID}/update")
    public ResponseEntity<APIResponse> updateImage(@RequestBody MultipartFile file, @PathVariable Long imageId){
        try {
            Image image= imageService.getImageById(imageId);
            if(image!=null){
                imageService.updateImage(file,imageId);
                return ResponseEntity.ok(new APIResponse("update Success",null));
            }
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("update failed", INTERNAL_SERVER_ERROR));

    }

    @DeleteMapping("/image/{imageID}/delete")
    public ResponseEntity<APIResponse> deleteImage( @PathVariable Long imageId){
        try {
            Image image= imageService.getImageById(imageId);
            if(image!=null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new APIResponse("delete Success",null));
            }
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("delete failed", INTERNAL_SERVER_ERROR));

    }

}
