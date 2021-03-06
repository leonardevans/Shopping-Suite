package com.shoppingsuite.web.controller.impl;

import com.shoppingsuite.persistence.model.Cart;
import com.shoppingsuite.persistence.model.Deal;
import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.persistence.model.User;
import com.shoppingsuite.security.AuthUtil;
import com.shoppingsuite.service.CartService;
import com.shoppingsuite.service.DealService;
import com.shoppingsuite.service.ProductCategoryService;
import com.shoppingsuite.service.ProductService;
import com.shoppingsuite.utils.CartUtil;
import com.shoppingsuite.web.controller.IMainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class MainController implements IMainController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    ProductService productService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    DealService dealService;

    @Autowired
    private CartUtil cartUtil;

    @Autowired

    @Override
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/testUserLogin")
    public String testUserLogin() {
        System.out.println("logginIn");
        try {
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken("bizbedu@gmail.com","adminPass22");
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            return "redirect:/";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:/login?failed";
        }

    }

    @Override
    @GetMapping("/")
    public String showIndexPage(Model model, HttpSession httpSession) {
        model.addAttribute("productCategories", productCategoryService.getAll());
        List<Deal> deals = dealService.getAll(1, 9, "endDate", "asc").getContent();
        model.addAttribute("deals", deals);
        cartUtil.updateCart(httpSession);
        return findPaginated(1, "name", "asc",  model, "/index");
    }

    @GetMapping("/product/{productId}")
    @Override
    public String showProductPage(Model model, @PathVariable Long productId) {
        Optional<Product> product = productService.getById(productId);

        if (product.isEmpty()){
            return "/index";
        }

        model.addAttribute("product", product.get());
        model.addAttribute("productCategories", productCategoryService.getAll());
        return "/product";
    }

    @GetMapping("/products/{productCatId}")
    @Override
    public String showProductsPage(Model model, @PathVariable Long productCatId) {
        Page<Product> productPage = productService.getAllByCategoryId(productCatId, 1, 10);

        model.addAttribute("pageSize", 10);
        model.addAttribute("category", productCategoryService.getById(productCatId).get());
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("productCategories", productCategoryService.getAll());
        return "/products";
    }

    @GetMapping("/deals")
    @Override
    public String showDealsPage(Model model) {
        model.addAttribute("productCategories", productCategoryService.getAll());
        model.addAttribute("deals", dealService.getAllByEndDateBefore(1, 10, new Date()));
        return null;
    }

    @GetMapping("/deal/{dealId}")
    @Override
    public String showDealPage(Model model,@PathVariable Long dealId) {
        model.addAttribute("productCategories", productCategoryService.getAll());
        model.addAttribute("deal", dealService.getById(dealId).get());
        return "/deal";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam String search){
        List<Product> products = productService.search(search);
        model.addAttribute("products", products);

        model.addAttribute("productCategories", productCategoryService.getAll());

        //get the deals for these products
        List<Deal> deals = new ArrayList<>();
        products.forEach(product -> {
            deals.addAll(product.getDeals());
        });

        model.addAttribute("deals", deals);
        return "/index";
    }

    @Override
    @GetMapping("/cart")
    public String showCartPage(Model model, HttpSession httpSession) {
        Cart cart = cartUtil.updateCart(httpSession);
        model.addAttribute("cart", cart);

        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        cart.getCartProducts().stream().forEach(cartProduct -> {
            double subTotal = cartProduct.getPrice()* cartProduct.getQuantity();
            total.updateAndGet(v -> new Double((double) (v + subTotal)));
        });

        model.addAttribute("total", total.get());
        return "/cart";
    }

    @Override
    public String showCheckoutPage(Model model) {
        return null;
    }


    @GetMapping("/page/{pageNo}")
    @Override
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model, String returnPage) {
        int pageSize = 10;

        Page<Product> page = productService.getAll(pageNo, pageSize, sortField, sortDir);
        List<Product> products = page.getContent();

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("products", products);
        return returnPage;
    }
}
