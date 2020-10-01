package com.shop.controller;

import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;
    private ProductService productService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/all")
    public String allCategories(Model model) {
        List<Category> categories = categoryService.getAllRoot();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/clothes")
    public String clothes(Model model) {
        List<Category> clothes = categoryService.getAllClothes();
        model.addAttribute("clothes", clothes);
        return "clothes";
    }

    @GetMapping("/clothes/women")
    public String women(Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "sortBy", defaultValue = "name") String sortField) {
        Page<Product> women = productService.getProductsPage("women", page - 1);
        int totalPages = women.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("women", women);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sortField);
        return "women";
    }
}
