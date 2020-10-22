package com.shop.controller;

import com.shop.entity.Order;
import com.shop.entity.User;
import com.shop.enumeration.Role;
import com.shop.enumeration.Status;
import com.shop.service.AdminService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    private UserService userService;

    @Autowired
    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
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

    @GetMapping("/users")
    public String users(Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "email", required = false) String email) {
        if (email != null) {
            model.addAttribute("totalPages", 1);
            Optional<User> userOptional = userService.getUserOptionalByEmail(email);
            model.addAttribute("users", userOptional.isPresent() ? Collections.singleton(userOptional.get()) : Collections.emptyList());
            model.addAttribute("currentPage", 1);
        }
        else {
            Page<User> users = userService.getAllUsers(page - 1);
            int totalPages = users.getTotalPages();
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("users", users);
            model.addAttribute("currentPage", page);
        }
        return "users";
    }

    @GetMapping("/users/block/{id}")
    public String blockUser(@PathVariable long id) {
        userService.changeRole(id, Role.BLOCKED);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/unblock/{id}")
    public String unblockUser(@PathVariable long id) {
        userService.changeRole(id, Role.USER);
        return "redirect:/admin/users";
    }
}
