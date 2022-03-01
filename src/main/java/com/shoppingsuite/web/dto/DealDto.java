package com.shoppingsuite.web.dto;

import com.shoppingsuite.persistence.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DealDto {
    private Long id;

    @NotNull(message = "Deal price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Please enter correct deal price value")
    @Digits(integer = 5, fraction = 2)
    private double dealPrice;

    private Product product;
    private Long productId;

    @NotEmpty(message = "Start date is required")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Date startDate;

    @NotEmpty(message = "End date is required")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Date endDate;
    private boolean published;

    public DealDto(Product product) {
        this.product = product;
        this.productId = product.getId();
    }
}
