package com.shop.controller;

import com.shop.entity.User;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
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
        return "redirect:/all";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, User user) {
        model.addAttribute("user", user);
        return "redirect:/all";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("user", null);
        return "redirect:/all";
    }
}
