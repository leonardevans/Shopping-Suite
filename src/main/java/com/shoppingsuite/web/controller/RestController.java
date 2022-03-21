package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.model.Review;
import com.shoppingsuite.persistence.model.User;
import com.shoppingsuite.security.AuthUtil;
import com.shoppingsuite.service.ProductService;
import com.shoppingsuite.service.ReviewService;
import com.shoppingsuite.web.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private ReviewService reviewService;
    private ProductService productService;
    private AuthUtil authUtil;

    @Autowired
    public RestController(ReviewService reviewService, ProductService productService, AuthUtil authUtil) {
        this.reviewService = reviewService;
        this.productService = productService;
        this.authUtil = authUtil;
    }

    @PostMapping("/make-review")
    public ResponseEntity makeReview(@RequestBody ReviewDto reviewDto){
        Review review = new Review();
        review.setProduct(productService.getById(reviewDto.getProductId()).get());
        User loggedInUser = authUtil.getLoggedInUser();

        if (loggedInUser == null){
            return ResponseEntity.status(403).body("Please sign in to make a review");
        }

        review.setUser(loggedInUser);
        return ResponseEntity.ok(reviewService.save(review));
    }
}
