package com.shoppingsuite.web.dto;

import com.shoppingsuite.persistence.model.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class DealDto {
    private Long id;

    @NotNull(message = "Deal price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Please enter correct deal price value")
    @Digits(integer = 5, fraction = 2)
    private double dealPrice;

    private Product product;
    private Long productId;

    @NotNull(message = "Start date is required")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Date startDate;

    @NotNull(message = "End date is required")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Date endDate;
    private boolean published;

    public DealDto(Product product) {
        this.product = product;
        this.productId = product.getId();
    }
}
