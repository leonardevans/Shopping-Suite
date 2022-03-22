package com.shoppingsuite.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDto {
    private Long productId;
    private String detail;
}
