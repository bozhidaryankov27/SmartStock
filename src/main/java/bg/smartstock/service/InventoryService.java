package bg.smartstock.service;

import bg.smartstock.model.Category;
import bg.smartstock.model.Product;
import bg.smartstock.model.StockStatus;
import bg.smartstock.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public final class InventoryService {
    private final ProductRepository repository;

    public InventoryService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product addProduct(
            String stockNumber,
            String name,
            Category category,
            BigDecimal price,
            int quantity,
            int minimumQuantity
    ) {
        Product product = new Product(UUID.randomUUID(), stockNumber, name, category, price, quantity, minimumQuantity);
        return repository.save(product);
    }

    public boolean deleteProduct(UUID id) {
        return repository.deleteById(id);
    }


    public Product restockProducts(UUID id, int amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive!");
        }

        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Product updatedProduct = new Product(
                product.id(),
                product.stockNumber(),
                product.name(),
                product.category(),
                product.price(),
                product.quantity() + amount,
                product.minimumQuantity());

        return repository.update(updatedProduct);
    }

    public Product sellProducts(UUID id, int amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if(amount > product.quantity()) {
            throw new IllegalArgumentException("Insufficient quantity");
        }

        Product updatedProduct = new Product(
                product.id(),
                product.stockNumber(),
                product.name(),
                product.category(),
                product.price(),
                product.quantity() - amount,
                product.minimumQuantity());

        System.out.println("Product is " + updatedProduct.stockStatus());
        return repository.update(updatedProduct);
    }

    public List<Product> viewAllProducts() {
        return repository.findAll();
    }

    public Product findProductByStockNumber(String stockNumber) {
        return repository.findByStockNum(stockNumber)
                .orElseThrow(() -> new IllegalArgumentException("Product not found!"));
    }

    public List<Product> getLowStockProducts() {
        return repository.findAll()
                .stream()
                .filter(product -> product.stockStatus() != StockStatus.IN_STOCK)
                .toList();
    }
}
