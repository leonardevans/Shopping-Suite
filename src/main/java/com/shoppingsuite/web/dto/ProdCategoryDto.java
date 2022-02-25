package com.shoppingsuite.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
public class ProdCategoryDto {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotNull(message = "Product Category name is required")
    private String name;
}
