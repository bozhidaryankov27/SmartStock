package bg.smartstock.model;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(
        UUID id,
        String stockNumber,
        String name,
        Category category,
        BigDecimal price,
        int quantity,
        int minimumQuantity
){
    public Product {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }

        if(stockNumber == null) {
            throw new IllegalArgumentException("ID can't be null");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can't be empty");
        }

        if (category == null) {
            throw new IllegalArgumentException("Category can't be null");
        }

        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException("Price can't be negative");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity can't be negative");
        }

        if (minimumQuantity < 0) {
            throw new IllegalArgumentException(
                    "Minimum quantity can't be negative"
            );
        }
    }

    public StockStatus stockStatus() {
        if (quantity == 0) {
            return StockStatus.OUT_STOCK;
        }

        if(quantity <= minimumQuantity) {
            return StockStatus.LOW_STOCK;
        }

        return StockStatus.IN_STOCK;
    }
}
