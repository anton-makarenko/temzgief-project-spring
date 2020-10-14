package com.shop.controller;

import com.shop.entity.Order;
import com.shop.enumeration.Status;
import com.shop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/orders")
    public String orders(Model model,
                         @RequestParam(name = "page", defaultValue = "1") int page,
                         @RequestParam(name = "status", required = false) Status... statuses) {
        Page<Order> orders = adminService.getOrdersPage(page - 1, statuses);
        int totalPages = orders.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        return "orders";
    }

    @PostMapping("/orders")
    public String orders(Model model,
                         @RequestParam("email") String email,
                         @RequestParam(name = "page", defaultValue = "1") int page,
                         @RequestParam(name = "status", required = false) Status... statuses) {
        Page<Order> orders = adminService.getOrdersPage(email, page - 1, statuses);
        int totalPages = orders.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        return "orders";
    }
}
