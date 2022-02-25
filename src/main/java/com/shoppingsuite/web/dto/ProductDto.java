package com.shoppingsuite.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class ProductDto {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotNull(message = "Product name is required")
    private String name;

    @Getter
    @Setter
    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price should be greater than 0")
    @Digits(integer = 5, fraction = 2)
    private double price;

    @Getter
    @Setter
    @NotNull(message = "Product stock is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Please enter correct stock value")
    @Digits(integer = 5, fraction = 2)
    private double stock;

    @Getter
    @Setter
    private boolean published;
}
