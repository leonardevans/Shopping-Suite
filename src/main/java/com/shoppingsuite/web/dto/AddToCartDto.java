package com.shoppingsuite.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddToCartDto {
    @NotNull(message = "product Id may not be null")
    private Long productId;

    @NotNull(message = "quantity may not be null")
    private int quantity;
}
