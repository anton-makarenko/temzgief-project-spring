package com.shop.controller;

import com.shop.config.constant.Constants;
import com.shop.entity.Category;
import com.shop.entity.Clothes;
import com.shop.enumeration.Color;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
@RequestMapping("/")
public class CategoryController {
    private CategoryService categoryService;
    private ProductService productService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping({"/all", "/"})
    public String allCategories(Model model) {
        categoryService.getAllRoot().collectList().subscribe(categories -> {
            model.addAttribute("categories", categories);
        });
        return "categories";
    }

    @GetMapping("/clothes")
    public String clothes(Model model) {
        categoryService.getAllByParent("clothes").collectList().subscribe(categories -> {
            model.addAttribute("category", categories);
        });
        return "clothes";
    }

    @GetMapping("/clothes/{category}")
    public String clothesCategory(Model model,
                                  @PathVariable String category,
                                  @RequestParam(name = "page", defaultValue = "1") int page,
                                  @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
                                  @RequestParam(name = "descending", defaultValue = "false") boolean descending,
                                  @RequestParam(name = "min", defaultValue = "0") double min,
                                  @RequestParam(name = "max", defaultValue = Constants.MAX_CLOTHES) double max,
                                  @RequestParam(name = "color", required = false) Color... colors) {
        Page<Clothes> products = productService.getClothesPage(category, min, max, sortBy, descending, page - 1, colors);
        int totalPages = products.getTotalPages();
        model.addAttribute("products", products);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("descending", descending);
        model.addAttribute("colors", colors);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        return category;
    }
}
