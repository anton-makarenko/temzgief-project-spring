package com.shop.controller;

import com.shop.entity.Product;
import com.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/add/{productId}")
    public String add(@PathVariable long productId) {
        cartService.addProductToCart(productId);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{productId}")
    public String remove(@PathVariable long productId) {
        cartService.removeProductFromCart(productId);
        return "redirect:/cart";
    }

    @GetMapping("/submit")
    public String submit() {
        cartService.submitOrdersInCart();
        return "redirect:/all";
    }

    @GetMapping
    public String cart(Model model,
                       @RequestParam(name = "page", defaultValue = "1") int page,
                       @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
                       @RequestParam(name = "descending", defaultValue = "false") boolean descending) {
        Page<Product> products = cartService.getClothesInCart(page - 1, sortBy, descending);
        model.addAttribute("productsInCart", products);
        model.addAttribute("products", products);
        int totalPages = products.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("descending", descending);
        return "cart";
    }
}
