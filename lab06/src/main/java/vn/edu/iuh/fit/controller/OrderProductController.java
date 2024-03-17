package vn.edu.iuh.fit.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.fit.repository.OrderProductRepo;
import vn.edu.iuh.fit.service.OrderService;

@AllArgsConstructor
@RequestMapping("/product")
@Controller
public class OrderProductController {
    private OrderService orderService;
    private OrderProductRepo orderProductRepo;

}
