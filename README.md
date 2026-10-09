
# SmartStock – Inventory Management System

SmartStock is a Java console application built to help small businesses keep track of their inventory.

The application allows users to manage products, update stock quantities, record sales, and quickly identify products that need restocking.

I developed this project to improve my Java programming skills and gain hands-on experience working with databases, JDBC, and layered application architecture.

## Features

- Add new products with a unique stock number
- View all available products
- Search for products by stock number
- Update stock quantities when restocking
- Process sales and automatically reduce stock
- Remove products from inventory
- Check products with low or zero stock
- Validate user input and handle invalid operations
- Store inventory data in a local SQLite database

## Technologies

- **Java 17** – Core application logic
- **SQLite** – Local database for storing products
- **JDBC** – Database connectivity and SQL operations
- **Maven** – Dependency management and project build
- **IntelliJ IDEA** – Development environment

## Project Structure

The project follows a layered structure to separate responsibilities and keep the code organized.

```text
src/main/java/bg/smartstock/
├── app/
│   └── ConsoleApp.java
├── model/
│   ├── Product.java
│   └── Category.java
├── repository/
│   ├── ProductRepository.java
│   └── SQLProductRepository.java
└── service/
    └── InventoryService.java
```

- **Model:** Represents product information and categories.
- **Repository:** Handles database operations using JDBC.
- **Service:** Contains business logic and input validation.
- **App:** Manages the console interface and user interaction.

## How It Works

Each product has a unique UUID and stock number, along with its name, category, price, quantity, and minimum stock level.

Users interact with the application through a console menu.

When a product is sold, its quantity is reduced. When products are restocked, the existing quantity is increased.

The application prevents sales when there is insufficient stock and helps identify products that need to be reordered.

All changes are saved in SQLite, allowing inventory data to persist between application runs.

## Getting Started

### Requirements

- Java JDK 17 or newer
- Maven
- IntelliJ IDEA or another Java IDE

### Installation

1. Clone the repository:

```bash
git clone https://github.com/YOUR_USERNAME/SmartStock.git
```

2. Open the project in IntelliJ IDEA.

3. Load the Maven dependencies.

4. Run the main application class.

5. Follow the console menu to manage products.

## What I Learned

Working on SmartStock helped me practice:

- Object-oriented programming in Java
- Working with relational databases using JDBC
- Writing SQL queries and implementing CRUD operations
- Separating business logic from data access
- Using Java records, enums, UUIDs, and BigDecimal
- Handling exceptions and validating user input
- Organizing code into maintainable packages

## Future Improvements

- Product filtering and sorting
- Sales history and reporting
- CSV import and export
- Unit testing with JUnit
- Graphical user interface

## About

SmartStock is a personal project focused on practicing Java backend development and building a practical inventory management application.
