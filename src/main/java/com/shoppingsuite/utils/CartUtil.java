package com.shoppingsuite.utils;

import com.shoppingsuite.persistence.model.Cart;
import com.shoppingsuite.persistence.model.User;
import com.shoppingsuite.security.AuthUtil;
import com.shoppingsuite.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class CartUtil {
    @Autowired
    CartService cartService;

    @Autowired
    AuthUtil authUtil;

    public Cart updateCart(HttpSession httpSession){
        User loggedInUser = authUtil.getLoggedInUser();

        Cart userCart = new Cart();

        //get the cart saved in the database
        if (loggedInUser != null){
            //get the logged in user cart
            Optional<Cart> cart = cartService.getByUserAndOrdered(loggedInUser, false);
            if (cart.isPresent()){
                userCart = cart.get();
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

        //set the cart to session variable
        httpSession.setAttribute("cart", userCart);

        return userCart;
    }
}
