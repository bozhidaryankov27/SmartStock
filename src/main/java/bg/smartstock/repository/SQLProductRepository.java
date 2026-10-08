package bg.smartstock.repository;

import bg.smartstock.database.DatabaseManager;
import bg.smartstock.model.Category;
import bg.smartstock.model.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLProductRepository implements ProductRepository{
    @Override
    public Product save(Product product) {
        String sql = """
        INSERT INTO Products
        (id, name, price, category, quantity, minimumQuantity, stockNumber)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try(
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
                ) {
            statement.setString(1, product.id().toString());
            statement.setString(2, product.name());
            statement.setString(3, product.price().toPlainString());
            statement.setString(4, product.category().name());
            statement.setInt(5, product.quantity());
            statement.setInt(6, product.minimumQuantity());
            statement.setString(7, product.stockNumber());

            statement.executeUpdate();
            return product;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Product update(Product product) {
        String sql = """
        UPDATE Products
        
        SET name = ?,
        price = ?,
        category = ?,
        quantity = ?,
        minimumQuantity = ?
        WHERE id = ? """;

        try(
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, product.name());
            statement.setString(2, product.price().toPlainString());
            statement.setString(3, product.category().name());
            statement.setInt(4, product.quantity());
            statement.setInt(5, product.minimumQuantity());
            statement.setString(6, product.id().toString());

            int affectedRows = statement.executeUpdate();

            if(affectedRows == 0) {
                throw new RuntimeException("Product not found");
            }
            return product;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findById(UUID id) {
        String sql = """
                SELECT * FROM
                Products WHERE id = ?
                """;

        try(
                Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id.toString());

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Product product = new Product(
                        UUID.fromString(resultSet.getString("id")
                        ),resultSet.getString("stockNumber"), resultSet.getString("name"), Category.valueOf(resultSet.getString("category")), new BigDecimal(resultSet.getString("price")), resultSet.getInt("quantity"), resultSet.getInt("minimumQuantity")
                );

                return Optional.of(product);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Optional<Product> findByStockNum(String stockNumber) {
        String sql = """
                SELECT * FROM
                Products WHERE stockNumber = ?
                """;

        try(
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, stockNumber);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Product product = new Product(
                        UUID.fromString(resultSet.getString("id")
                        ),resultSet.getString("stockNumber"), resultSet.getString("name"), Category.valueOf(resultSet.getString("category")), new BigDecimal(resultSet.getString("price")), resultSet.getInt("quantity"), resultSet.getInt("minimumQuantity")
                );

                return Optional.of(product);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Product> findAll() {
        String sql = """
                SELECT * FROM
                Products 
                """;

        try(
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            List<Product> products = new ArrayList<>();
            while(resultSet.next()) {
                Product product = new Product(UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("stockNumber"),
                        resultSet.getString("name"),
                        Category.valueOf(resultSet.getString("category")),
                        new BigDecimal(resultSet.getString("price")),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("minimumQuantity")
                        );
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        String sql = """
                DELETE FROM
                Products WHERE id = ?
                """;

        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, id.toString());

            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
