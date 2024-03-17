package vn.edu.iuh.fit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.repository.OrderProductRepo;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderProductRepo orderProductRepo;
}
