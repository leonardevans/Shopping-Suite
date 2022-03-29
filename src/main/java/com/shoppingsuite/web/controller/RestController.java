package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.joins.CartProduct;
import com.shoppingsuite.persistence.model.Cart;
import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.persistence.model.Review;
import com.shoppingsuite.persistence.model.User;
import com.shoppingsuite.security.AuthUtil;
import com.shoppingsuite.service.CartService;
import com.shoppingsuite.service.ProductService;
import com.shoppingsuite.service.ReviewService;
import com.shoppingsuite.web.dto.AddToCartDto;
import com.shoppingsuite.web.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private ReviewService reviewService;
    private ProductService productService;
    private AuthUtil authUtil;
    private CartService cartService;

    @Autowired
    public RestController(ReviewService reviewService, ProductService productService, AuthUtil authUtil, CartService cartService) {
        this.reviewService = reviewService;
        this.productService = productService;
        this.authUtil = authUtil;
        this.cartService = cartService;
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
    public ResponseEntity addProductToCart(@RequestBody AddToCartDto addToCartDto, HttpSession httpSession) throws Exception {
        User loggedInUser = authUtil.getLoggedInUser();

        Cart userCart = null;

        //get the cart saved in the database
        if (loggedInUser != null){
            //get the logged in user cart
            Optional<Cart> cart = cartService.getByUserAndOrdered(loggedInUser, false);
            if (cart.isPresent()){
                userCart = cart.get();
            }else {
                userCart = new Cart();
            }
        }

        //get the cart saved in session
        Cart sessionCart = null;
        try {
            sessionCart = (Cart) httpSession.getAttribute("cart");
        }catch (Exception e){
            System.out.println("No cart saved in session");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        //merge the carts
        if (sessionCart != null){
            Cart finalUserCart = userCart;
            sessionCart.getCartProducts().forEach(cartProduct -> {
                //if userCart does not contain this session cartProduct we add it to the userCart
                if (!finalUserCart.getCartProducts().contains(cartProduct)){
                    finalUserCart.getCartProducts().add(cartProduct);
                }
            });

            userCart = finalUserCart;
        }

        //the product from db
        Product product = productService.getById(addToCartDto.getProductId()).orElseThrow(Exception::new);

        CartProduct cartProduct = new CartProduct(userCart, product, addToCartDto.getQuantity(), product.getPrice());

        userCart.getCartProducts().add(cartProduct);

        if (loggedInUser != null){
            userCart.setUser(loggedInUser);
        }

        //save the cart
        cartService.save(userCart);

        //set the cart to session variable
        httpSession.setAttribute("cart", userCart);

        return ResponseEntity.ok(true);
    }
}
