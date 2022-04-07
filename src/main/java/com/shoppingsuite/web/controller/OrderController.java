package com.shoppingsuite.web.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.shoppingsuite.persistence.model.Cart;
import com.shoppingsuite.persistence.model.Order;
import com.shoppingsuite.persistence.model.User;
import com.shoppingsuite.security.AuthUtil;
import com.shoppingsuite.service.CartService;
import com.shoppingsuite.service.OrderService;
import com.shoppingsuite.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/order/*")
public class OrderController {
    private OrderService orderService;
    private AuthUtil authUtil;
    private CartService cartService;
    PaypalService paypalService;

    @Value("${app.currency}")
    String currency;

    private static final String SUCCESS_URL = "/pay/success";
    private static final String CANCEL_URL = "/pay/cancel";

    @Value("${BASE_URL}")
    private String BASE_URL;

    @Autowired
    public OrderController(OrderService orderService, AuthUtil authUtil, CartService cartService, PaypalService paypalService) {
        this.orderService = orderService;
        this.authUtil = authUtil;
        this.cartService = cartService;
        this.paypalService = paypalService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/make")
    public String makeOrder(Model model) {
        User loggedInUser = authUtil.getLoggedInUser();

        //get the cart saved in the database
        //get the logged in user cart
        Optional<Cart> optionalCart = cartService.getByUserAndOrdered(loggedInUser, false);
        if (optionalCart.isEmpty()) {
            return "/cart";
        }

        Cart cart = optionalCart.get();

        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        cart.getCartProducts().stream().forEach(cartProduct -> {
            double subTotal = cartProduct.getPrice()* cartProduct.getQuantity();
            total.updateAndGet(v -> new Double((double) (v + subTotal)));
        });

        Order order = new Order(cart, total.get(),  currency);

        order = orderService.save(order);

        try{
            Payment payment = paypalService.createPayment(order.getAmount(), order.getCurrency(), "PAYPAL", "SALE", "buy items", BASE_URL + CANCEL_URL + "/" + order.getId(), BASE_URL + SUCCESS_URL + "/" + order.getId());

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return "redirect:/cart?payment_failed" + order.getId();
        }

        model.addAttribute("order", order);

        return "/orders/"+order.getId() + "?created";
    }
}
