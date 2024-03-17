package vn.edu.iuh.fit;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.entity.OrderProduct;
import vn.edu.iuh.fit.entity.Product;
import vn.edu.iuh.fit.repository.OrderProductRepo;
import vn.edu.iuh.fit.repository.ProductRepo;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@AllArgsConstructor
public class Lab06Application {
	private ProductRepo productRepo;
	private OrderProductRepo orderProductRepo;

	public static void main(String[] args) {
		SpringApplication.run(Lab06Application.class, args);
	}

//	@Bean
	CommandLineRunner create(){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				productRepo.save(new Product(
						UUID.randomUUID(),
						"Oi",
						10.0,
						100
				));

				productRepo.save(new Product(
						UUID.randomUUID(),
						"Xoai",
						20.0,
						100
				));
				List<Product> all = productRepo.findAll();

				UUID orderId = UUID.randomUUID();
				all.forEach(p->{
					orderProductRepo.save(
							new OrderProduct(
									UUID.randomUUID(),
									p,
									10,
									orderId
							)
					);
				});

			}
		};
	}
}


