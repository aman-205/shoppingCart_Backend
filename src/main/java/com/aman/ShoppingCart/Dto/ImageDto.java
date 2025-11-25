package com.aman.ShoppingCart.Dto;


public class ImageDto {
    private Long imageId;
    private String imageName;
    private String dwnldUrl;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDwnldUrl() {
        return dwnldUrl;
    }

    public void setDwnldUrl(String dwnldUrl) {
        this.dwnldUrl = dwnldUrl;
    }
}
