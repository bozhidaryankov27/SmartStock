package bg.smartstock.repository;

import bg.smartstock.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Product update(Product product);
    Optional<Product> findById(UUID id);
    Optional<Product> findByStockNum(String stockNumber);
    List<Product> findAll();
    boolean deleteById(UUID id);
}
