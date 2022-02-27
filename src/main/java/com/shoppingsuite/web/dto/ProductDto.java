package com.shoppingsuite.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

public class ProductDto {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotEmpty(message = "Product name is required")
    private String name;

    @Getter
    @Setter
    @NotEmpty(message = "Product price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price should be greater than 0")
    @Digits(integer = 5, fraction = 2)
    private double price;

    @Getter
    @Setter
    @NotEmpty(message = "Product stock is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Please enter correct stock value")
    @Digits(integer = 5, fraction = 2)
    private double stock;

    @Getter
    @Setter
    private String imageUrl;

    @Getter
    @Setter
    private MultipartFile image;

    @Getter
    @Setter
    private boolean published;
}
