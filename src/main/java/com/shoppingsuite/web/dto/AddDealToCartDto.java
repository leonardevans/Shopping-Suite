package com.shoppingsuite.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddDealToCartDto {
    @NotNull(message = "Deal Id may not be null")
    private Long dealId;

    @NotNull(message = "quantity may not be null")
    private int quantity;
}
