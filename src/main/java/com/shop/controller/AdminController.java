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
                         @RequestParam(required = false) String email) {
        Page<Order> orders;
        if (email == null || email.equals("null"))
            orders = adminService.getOrdersPage(page - 1, Status.REGISTERED, Status.PAID, Status.CANCELLED);
        else
            orders = adminService.getOrdersPage(email, page - 1, Status.REGISTERED, Status.PAID, Status.CANCELLED);
        int totalPages = orders.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("email", email);
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String changeStatus(@RequestParam(name = "page", defaultValue = "1") int page,
                               @RequestParam(name = "newStatus") Status status,
                               @PathVariable long id) {
        adminService.changeStatus(id, status);
        return "redirect:/admin/orders?page=" + page;
    }
}
