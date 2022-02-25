package com.shoppingsuite.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ProdCategoryDto {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;
}
