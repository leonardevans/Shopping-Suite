package com.shoppingsuite.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartDto {
    private Long productId;
    private int quantity;
}
