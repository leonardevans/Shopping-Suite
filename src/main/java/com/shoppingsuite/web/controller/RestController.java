package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.joins.CartProduct;
import com.shoppingsuite.persistence.model.*;
import com.shoppingsuite.security.AuthUtil;
import com.shoppingsuite.service.CartService;
import com.shoppingsuite.service.DealService;
import com.shoppingsuite.service.ProductService;
import com.shoppingsuite.service.ReviewService;
import com.shoppingsuite.utils.CartUtil;
import com.shoppingsuite.web.dto.AddDealToCartDto;
import com.shoppingsuite.web.dto.AddToCartDto;
import com.shoppingsuite.web.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private ReviewService reviewService;
    private ProductService productService;
    private AuthUtil authUtil;
    private CartService cartService;
    private CartUtil cartUtil;
    private DealService dealService;

    @Autowired
    public RestController(ReviewService reviewService, ProductService productService, AuthUtil authUtil, CartService cartService, CartUtil cartUtil, DealService dealService) {
        this.reviewService = reviewService;
        this.productService = productService;
        this.authUtil = authUtil;
        this.cartService = cartService;
        this.cartUtil = cartUtil;
        this.dealService = dealService;
    }

    @PostMapping(value = "/api/make-review")
    public ResponseEntity makeReview( @RequestBody ReviewDto reviewDto){
        User loggedInUser = authUtil.getLoggedInUser();

        if (loggedInUser == null){
            return ResponseEntity.status(403).body("Please sign in to make a review");
        }

        Review review = new Review(reviewDto.getDetail(), loggedInUser, productService.getById(reviewDto.getProductId()).get());
        return ResponseEntity.ok(reviewService.save(review));
    }

    @PostMapping(value = "/api/add-product-to-cart")
    public ResponseEntity addProductToCart(@Valid @RequestBody AddToCartDto addToCartDto, HttpSession httpSession) throws Exception {
        User loggedInUser = authUtil.getLoggedInUser();

        Cart userCart = cartUtil.updateCart(httpSession);

        //the product from db
        Product product = productService.getById(addToCartDto.getProductId()).orElseThrow(Exception::new);

        //find cart product with this product
        CartProduct existCartProduct = userCart.getCartProducts().stream().filter(cartProduct2 -> cartProduct2.getProduct().getId().equals(product.getId())).findFirst().orElse(null);

        if (existCartProduct == null){
            CartProduct cartProduct = new CartProduct(userCart, product, addToCartDto.getQuantity(), product.getPrice());
            userCart.getCartProducts().add(cartProduct);
        }

        if (loggedInUser != null){
            userCart.setUser(loggedInUser);

            //save the cart
            userCart = cartService.save(userCart);
        }


        //set the cart to session variable
        httpSession.setAttribute("cart", userCart);

        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/api/remove-product-from-cart")
    public ResponseEntity removeProductFromCart(@Valid @RequestBody AddToCartDto addToCartDto, HttpSession httpSession) throws Exception {
        User loggedInUser = authUtil.getLoggedInUser();

        Cart userCart = cartUtil.updateCart(httpSession);

        //get the product from db
        Product product = productService.getById(addToCartDto.getProductId()).orElseThrow(Exception::new);

        //remove product from cart
        userCart.getCartProducts().removeIf(cartProduct -> cartProduct.getProduct().getId().equals(product.getId()));

        if (loggedInUser != null){
            userCart.setUser(loggedInUser);

            //save the cart
            userCart = cartService.save(userCart);
        }


        //set the cart to session variable
        httpSession.setAttribute("cart", userCart);

        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/api/update-cart-product-quantity")
    public ResponseEntity updateCartProductQuantity(@Valid @RequestBody AddToCartDto addToCartDto, HttpSession httpSession) throws Exception {
        User loggedInUser = authUtil.getLoggedInUser();

        Cart userCart = cartUtil.updateCart(httpSession);

        //get the product from db
        Product product = productService.getById(addToCartDto.getProductId()).orElseThrow(Exception::new);

        //find cart product with this product
        CartProduct existCartProduct = userCart.getCartProducts().stream().filter(cartProduct2 -> cartProduct2.getProduct().getId().equals(product.getId())).findFirst().orElse(null);

        if (existCartProduct != null){
            userCart.getCartProducts().stream().filter(cartProduct -> cartProduct.getProduct().getId().equals(addToCartDto.getProductId()) ).findFirst().get().setQuantity(addToCartDto.getQuantity());
        }

        if (loggedInUser != null){
            userCart.setUser(loggedInUser);

            //save the cart
            userCart = cartService.save(userCart);
        }


        //set the cart to session variable
        httpSession.setAttribute("cart", userCart);

        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/api/clear-cart")
    public ResponseEntity clearCart( HttpSession httpSession) throws Exception {
        User loggedInUser = authUtil.getLoggedInUser();

        Cart userCart = cartUtil.updateCart(httpSession);

        //clear cart products
        userCart.getCartProducts().clear();

        if (loggedInUser != null){
            userCart.setUser(loggedInUser);

            //save the cart
            userCart = cartService.save(userCart);
        }


        //set the cart to session variable
        httpSession.setAttribute("cart", userCart);

        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/api/add-deal-to-cart")
    public ResponseEntity addDealToCart(@Valid @RequestBody AddDealToCartDto addDealToCartDto, HttpSession httpSession) throws Exception {
        User loggedInUser = authUtil.getLoggedInUser();

        Cart userCart = cartUtil.updateCart(httpSession);

        //get the deal from db
        Deal deal = dealService.getById(addDealToCartDto.getDealId()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        //find cart product with this product
        CartProduct existCartProduct = userCart.getCartProducts().stream().filter(cartProduct2 -> cartProduct2.getProduct().getId().equals(deal.getProduct().getId())).findFirst().orElse(null);

        if (existCartProduct == null){
            CartProduct cartProduct = new CartProduct(userCart, deal.getProduct(), addDealToCartDto.getQuantity(), deal.getDealPrice());
            userCart.getCartProducts().add(cartProduct);
        }else{
            userCart.getCartProducts().stream().filter(cartProduct -> cartProduct.getProduct().getId().equals(deal.getProduct().getId()) ).findFirst().get().setPrice(deal.getDealPrice());
        }

        if (loggedInUser != null){
            userCart.setUser(loggedInUser);

            //save the cart
            userCart = cartService.save(userCart);
        }


        //set the cart to session variable
        httpSession.setAttribute("cart", userCart);

        return ResponseEntity.ok(true);
    }
}
