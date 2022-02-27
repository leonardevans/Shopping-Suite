package com.shoppingsuite.web.dto;

import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.persistence.model.ProductCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    private Long id;


    @NotEmpty(message = "Product name is required")
    private String name;


    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price should be greater than 0")
    @Digits(integer = 5, fraction = 2)
    private double price;


    @NotNull(message = "Product stock is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Please enter correct stock value")
    @Digits(integer = 5, fraction = 2)
    private double stock;


    private String imageUrl;

    private MultipartFile image;


    private boolean published;

    @NotNull
    private Long productCategoryId;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.imageUrl = product.getImageUrl();
        this.published = product.isPublished();
        this.productCategoryId = product.getProductCategory().getId();
    }
}
