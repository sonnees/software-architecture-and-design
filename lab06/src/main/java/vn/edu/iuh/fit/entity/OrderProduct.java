package vn.edu.iuh.fit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "order_product")
public class OrderProduct {
    @Id
    private UUID id;
    @OneToOne
    private Product product;
    private int quan;

    private UUID orderID;

    public OrderProduct(UUID id) {
        this.id = id;
    }
}
