package vn.edu.iuh.fit.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.fit.entity.Product;
import vn.edu.iuh.fit.repository.ProductRepo;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/home")
@Controller
public class ProductController {
    private ProductRepo productRepo;

    @GetMapping("/")
    public String getAll(Model model){
        List<Product> all = productRepo.findAll();

        model.addAttribute("list", all);
        return "home";
    }
}
