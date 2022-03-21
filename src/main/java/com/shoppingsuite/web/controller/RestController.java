package com.shoppingsuite.web.controller;

import com.shoppingsuite.service.ProductService;
import com.shoppingsuite.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private ReviewService reviewService;
    private ProductService productService;

    @Autowired
    public RestController(ReviewService reviewService, ProductService productService) {
        this.reviewService = reviewService;
        this.productService = productService;
    }
}
