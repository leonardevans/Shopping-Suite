package com.shoppingsuite.web.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.shoppingsuite.persistence.dao.OrderRepo;
import com.shoppingsuite.persistence.enums.OrderStatus;
import com.shoppingsuite.persistence.model.Cart;
import com.shoppingsuite.persistence.model.Order;
import com.shoppingsuite.persistence.model.User;
import com.shoppingsuite.security.AuthUtil;
import com.shoppingsuite.service.CartService;
import com.shoppingsuite.service.OrderService;
import com.shoppingsuite.service.PaypalService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/orders/*")
public class OrderController {
    private OrderService orderService;
    private AuthUtil authUtil;
    private CartService cartService;
    private PaypalService paypalService;
    private OrderRepo orderRepo;

    @Value("${app.currency}")
    String currency;

    private static final String SUCCESS_URL = "/orders/success";
    private static final String CANCEL_URL = "/orders/cancel";

    @Value("${BASE_URL}")
    private String BASE_URL;

    @Autowired
    public OrderController(OrderService orderService, AuthUtil authUtil, CartService cartService, PaypalService paypalService, OrderRepo orderRepo) {
        this.orderService = orderService;
        this.authUtil = authUtil;
        this.cartService = cartService;
        this.paypalService = paypalService;
        this.orderRepo = orderRepo;
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
            return "redirect:/orders/payment_failed/" + order.getId();
        }

        model.addAttribute("order", order);

        return "/orders/"+order.getId() + "?created";
    }

    @GetMapping("/payment_failed/{orderId}")
    public String processOrderPaymentFailure(@PathVariable("orderId") long orderId, Model model) {
        Order order = orderRepo.findById(orderId).orElseThrow(RuntimeException::new);
        orderRepo.deleteById(orderId);

        Cart cart = order.getCart();
        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        cart.getCartProducts().stream().forEach(cartProduct -> {
            double subTotal = cartProduct.getPrice()* cartProduct.getQuantity();
            total.updateAndGet(v -> new Double((double) (v + subTotal)));
        });

        model.addAttribute("total", total.get());
        model.addAttribute("errorMessage", "Failed to complete payment process!");
        model.addAttribute("cart", cart);

        return "/cart";
    }

    @GetMapping("/cancel/{orderId}")
    public String processCancelOrderPayment(@PathVariable("orderId") long orderId, Model model) {
        orderRepo.deleteById(orderId);

        model.addAttribute("errorMessage", "Payment Cancelled!");

        return "/cart";
    }

    @GetMapping("/success/{orderId}")
    public String processSuccessOrderPayment(@PathVariable("orderId") long orderId, @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try
        {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toString());

            Order order = orderRepo.getById(orderId);
            order.setPayment_details(payment.getState());
            order.getCart().setOrdered(true);
            order.getCart().setTotal(order.getAmount());
            order.setOrderStatus(OrderStatus.PENDING);
            orderRepo.save(order);


            if (payment.getState().equals("approved"))
            {
                return "redirect:/orders/" + orderId +"?order_success";
            }
        }
        catch (PayPalRESTException e)
        {
            System.out.println(e.getMessage());
            if (e.getMessage().contains("PAYMENT_ALREADY_DONE")){
                return "redirect:/orders/" + orderId +"?order_success";
            }

            return "redirect:/orders/payment_failed/" + orderId;
        }

        return "redirect:/";
    }

    @GetMapping("/{orderId}")
    public String showOrder(Model model, @PathVariable Long orderId) throws NotFoundException {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new NotFoundException("No orders found with id: " + orderId));
        model.addAttribute("order", order);
        return "/order";
    }

    @GetMapping
    public String showUserOrders(Model model){
        model.addAttribute("orders", orderService.getByUserId(authUtil.getLoggedInUser().getId()));
        return "/orders";
    }
}
