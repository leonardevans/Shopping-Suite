package com.shoppingsuite.web.dto;

import com.shoppingsuite.persistence.model.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@NoArgsConstructor
public class ProdCategoryDto {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotEmpty(message = "Product Category name is required")
    @Size(min = 3, message = "Please enter at least 3 characters")
    private String name;

    public ProdCategoryDto(ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.name = productCategory.getName();
    }
}
