package vn.edu.iuh.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.entity.OrderProduct;

import java.util.UUID;

@Repository
public interface OrderProductRepo extends JpaRepository<OrderProduct, UUID> {
}
