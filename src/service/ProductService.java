package service;
import util.DatabaseActivityLogger;

// Package declaration and imports

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abchome_3", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ; // Assume you have a valid database connection
    // Methods for business logic related to products
    public void addProduct(Product product) {
        String sql = "INSERT INTO products (productName, description, purchasePrice, sellingPrice, quantity) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPurchasePrice());
            statement.setDouble(4, product.getSellingPrice());
            statement.setInt(5, product.getQuantity());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product added to the database successfully.");
                // Log the database insert activity
                DatabaseActivityLogger.logInsertActivity("products", getProductIDByName(product.getProductName()));
            } else {
                System.out.println("Failed to add product to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }



    public void updateProduct(Product product,int units) {
        String sql = "UPDATE Products SET productName = ?, description = ?, purchasePrice = ?, sellingPrice = ?, quantity = ? WHERE productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPurchasePrice());
            statement.setDouble(4, product.getSellingPrice());
            statement.setInt(5, product.getQuantity());
            statement.setInt(6, product.getProductId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product updated in the database successfully.");
                // Log the database update activity
                DatabaseActivityLogger.logUpdateActivity("products", product.getProductId());
            } else {
                System.out.println("Failed to update product in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }

    public void removeProduct(int productId) {
        String sql = "DELETE FROM Products WHERE productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product removed from the database successfully.");
                // Log the database remove activity
                DatabaseActivityLogger.logRemoveActivity("Products", productId);
            } else {
                System.out.println("Failed to remove product from the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }

    public Product searchProduct(int productId) {
        String sql = "SELECT * FROM products WHERE ProductID = ?";
        Product product = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // If the result set is not empty, create a Product object with the retrieved details
                    product = new Product();
                    product.setProductId(resultSet.getInt("productID"));
                    product.setProductName(resultSet.getString("productName"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPurchasePrice(resultSet.getDouble("purchasePrice"));
                    product.setSellingPrice(resultSet.getDouble("sellingPrice"));
                    product.setQuantity(resultSet.getInt("quantity"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return product;
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // For each row in the result set, create a Product object with the retrieved details
                Product product = new Product();
                product.setProductId(resultSet.getInt("productID"));
                product.setProductName(resultSet.getString("productName"));
                product.setDescription(resultSet.getString("description"));
                product.setPurchasePrice(resultSet.getDouble("purchasePrice"));
                product.setSellingPrice(resultSet.getDouble("sellingPrice"));
                product.setQuantity(resultSet.getInt("quantity"));

                // Add the product to the list
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return products;
    }

    // Additional methods if needed
    private int getProductIDByName(String productName) {
        String sql = "SELECT productID FROM products WHERE productName = ?";
        int productID = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, productName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    productID = resultSet.getInt("productID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return productID;
    }

    public Product getProductById(int productId) {
        String sql = "SELECT * FROM products WHERE ProductID = ?";
        Product product = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // If the result set is not empty, create a Product object with the retrieved details
                    product = new Product();
                    product.setProductId(resultSet.getInt("productID"));
                    product.setProductName(resultSet.getString("productName"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPurchasePrice(resultSet.getDouble("purchasePrice"));
                    product.setSellingPrice(resultSet.getDouble("sellingPrice"));
                    product.setQuantity(resultSet.getInt("quantity"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return product;
    }


    private int getProductQuantityInDatabase(int productId) {
        String sql = "SELECT quantity FROM products WHERE productID = ?";
        int currentQuantity = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve the available quantity from the result set
                    currentQuantity = resultSet.getInt("quantity");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return currentQuantity;
    }

    public void updateProductQuantity(int productId, int unitsSold) {
        // Assuming you have a method to retrieve the current available quantity
        int currentQuantity = getProductQuantityInDatabase(productId);

        // Calculate the new available quantity after selling the units
        int newQuantity = currentQuantity - unitsSold;

        // Update the product quantity in the database
        updateProductQuantityInDatabase(productId, newQuantity);
    }

    private void updateProductQuantityInDatabase(int productId, int newQuantity) {
        String sql = "UPDATE products SET quantity = ? WHERE productID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newQuantity);
            statement.setInt(2, productId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product quantity updated successfully.");
                // Log the database update activity
                DatabaseActivityLogger.logUpdateActivity("products", productId);
            } else {
                System.out.println("Failed to update product quantity. Product ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }
}
