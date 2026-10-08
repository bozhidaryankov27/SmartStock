package bg.smartstock.app;

import bg.smartstock.model.Category;
import bg.smartstock.model.Product;
import bg.smartstock.service.InventoryService;

import java.math.BigDecimal;
import java.util.Scanner;

public final class ConsoleApp {
    private final InventoryService service;
    private final Scanner scanner;

    public ConsoleApp(InventoryService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    private String buildHeader() {
        StringBuilder builder = new StringBuilder();

        builder.append("----------------------------------------------\n");
        builder.append("             S M A R T S T O C K\n");
        builder.append("          Inventory Management System\n");
        builder.append("----------------------------------------------\n");

        return builder.toString();
    }

    private String buildMenu() {
        StringBuilder builder = new StringBuilder();

        builder.append("\n");
        builder.append("               MAIN MENU\n");
        builder.append("-----------------------------------\n");
        builder.append("  1. Show all products\n");
        builder.append("  2. Add new product\n");
        builder.append("  3. Remove product\n");
        builder.append("  4. Restock product\n");
        builder.append("  5. Sell product\n");
        builder.append("  6. View low stock products\n");
        builder.append("  7. Search product by stockNumber\n");
        builder.append("  0. Exit\n");
        builder.append("------------------------------------\n");
        builder.append("  Choose an option: ");

        return builder.toString();
    }

    public void start() {
        System.out.println(buildHeader());

        boolean running = true;

        while(running) {
            System.out.println(buildMenu());

            String option = scanner.nextLine().trim();

            switch(option) {
                case "1" -> showAllProducts();
                case "2" -> addNewProduct();
                case "3" -> removeProduct();
                case "4" -> restockProduct();
                case "5" -> sellProduct();
                case "6" -> viewLowStockProducts();
                case "7" -> searchProductByStockNumber();
                case "0" -> {
                    System.out.println("Exiting SmartStock...");
                    running = false;
                }
                default -> System.out.println("\nInvalid option!");
            }
        }
    }

    private void printProduct(Product product) {
        System.out.println("\n-----------------------------------");
        System.out.println("Stock number:     " + product.stockNumber());
        System.out.println("Name:             " + product.name());
        System.out.println("Category:         " + product.category());
        System.out.println("Price:            " + product.price());
        System.out.println("Quantity:         " + product.quantity());
        System.out.println("Minimum quantity: " + product.minimumQuantity());
        System.out.println("Status:           " + product.stockStatus());
        System.out.println("-----------------------------------");
    }

    private void showAllProducts() {
        if(service.viewAllProducts().isEmpty()) {
            System.out.println("\nNo products found!");
        }
        service.viewAllProducts().forEach(System.out::println);
    }

    private void addNewProduct() {
        System.out.println("Stock Number: ");
        String stockNumber = scanner.nextLine().trim();

        System.out.println("Name: ");
        String name = scanner.nextLine().trim();

        System.out.println("Category: ");
        Category category = Category.valueOf(scanner.nextLine().trim());

        System.out.println("Price: ");
        BigDecimal price = new BigDecimal(scanner.nextLine().trim());

        System.out.println("Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("Minimum quantity: ");
        int minimumQuantity = Integer.parseInt(scanner.nextLine().trim());

        try{
            Product product = service.addProduct(stockNumber, name, category, price, quantity, minimumQuantity);
            System.out.println("\nProduct added successfully!");
            printProduct(product);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeProduct() {
        System.out.println("Enter stock number which product you want to remove: ");
        String stockNumber = scanner.nextLine().trim();
        try {
            Product product = service.findProductByStockNumber(stockNumber);

            boolean deleted = service.deleteProduct(product.id());

            if(deleted) {
                System.out.println("\nProduct was removed successfully!");
            } else {
                System.out.println("\nProduct couldn't be removed!");
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void restockProduct() {
        System.out.println("Enter stock number which product you want to restock: ");
        String stockNumber = scanner.nextLine().trim();

        System.out.println("Enter quantity to add: ");
        int amount = Integer.parseInt(scanner.nextLine().trim());

        try {
            Product product = service.findProductByStockNumber(stockNumber);

            Product updatedProduct = service.restockProducts(product.id(), amount);

            System.out.println("\nProduct is restocked successfully!");
            printProduct(updatedProduct);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sellProduct() {
        System.out.println("Enter stock number: ");
        String stockNumber = scanner.nextLine().trim();
        Product product;

        try {
            product = service.findProductByStockNumber(stockNumber);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return;
        }

        while(true) {
            try {
                System.out.println("Enter quantity to sell: ");
                int amount = Integer.parseInt(scanner.nextLine().trim());
                Product updatedProduct = service.sellProducts(product.id(), amount);

                System.out.println("\nSale completed!");
                printProduct(updatedProduct);

                break;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number!");
            } catch (IllegalArgumentException e) {
                System.out.println("Please try again!");
            }
        }
    }

    private void viewLowStockProducts() {
        var products = service.getLowStockProducts();

        if(products.isEmpty()) {
            System.out.println("No LOW_STOCK products found!");
            return;
        }

        System.out.println("\nLOW_STOCK PRODUCTS");

        products.forEach(this::printProduct);
    }

    private void searchProductByStockNumber() {
        while(true) {
            System.out.println("Enter stock number: ");
            String stockNumber = scanner.nextLine().trim();
            try {
                Product product = service.findProductByStockNumber(stockNumber);
                System.out.println("Product is found!\n");
                printProduct(product);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong stock number. Try again!");
            }
        }
    }
}
