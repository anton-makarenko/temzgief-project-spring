package com.shop.controller;

import com.shop.entity.User;
import com.shop.repository.UserRepository;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, User user) {
        userService.saveUser(user);
        model.addAttribute("user", user);
        return "categories";
    }

    @GetMapping("/logout")
    public String logout(Model model, User user) {
        return "categories";
    }
}
