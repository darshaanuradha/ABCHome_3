package service;

// Package declaration and imports

import model.Customer;
import model.Invoice;
import model.Product;
import util.DatabaseActivityLogger;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceService {
    private static Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abchome_3", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Methods for business logic related to invoices


    public Invoice generateInvoice(Customer customer, Product product, int units) {
        // Assuming there is a logic to calculate total price, discount, and generate a unique invoice number
        double unitPrice = product.getSellingPrice();
        double totalPrice = units * unitPrice;
        double discount = calculateDiscount(units,unitPrice);
        double discountedTotalPrice = totalPrice - discount;

        // Create a new Invoice object
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(generateUniqueInvoiceNumber());
        invoice.setInvoiceDate(String.valueOf(LocalDateTime.now()));
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setProductId(product.getProductId());
        invoice.setUnits(units);
        invoice.setUnitPrice(unitPrice);
        invoice.setTotalPrice(totalPrice);
        invoice.setDiscount(discount);
        invoice.setDiscountedTotalPrice(discountedTotalPrice);

        // Assuming there is a logic to update the product available quantity
        ProductService pd = new ProductService();
        pd.updateProductQuantity(product.getProductId(),units);

        // Assuming there is a logic to insert the invoice details into the database
        insertInvoiceToDatabase(invoice);


        return invoice;
    }

    // Additional methods...
    public void insertInvoiceToDatabase(Invoice invoice) {
        String sql = "INSERT INTO invoices (invoiceNumber, invoiceDate, customerID, productID, unitPrice, units, totalPrice, discount, discountedTotalPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, invoice.getInvoiceNumber());
            statement.setString(2, invoice.getInvoiceDate());
            statement.setInt(3, invoice.getCustomerId());
            statement.setInt(4, invoice.getProductId());
            statement.setDouble(5, invoice.getUnitPrice());
            statement.setInt(6, invoice.getUnits());
            statement.setDouble(7, invoice.getTotalPrice());
            statement.setDouble(8, invoice.getDiscount());
            statement.setDouble(9, invoice.getDiscountedTotalPrice());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Invoice details inserted into the database successfully.");
                // Log the database invoice generation activity
                DatabaseActivityLogger.logInsertActivity("Invoices", invoice.getInvoiceNumber());
            } else {
                System.out.println("Failed to insert invoice details into the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }
    private double calculateDiscount(int units, double unitPrice) {
        return units > 10 ? 0.1 * units * unitPrice : 0;
    }

    private int generateUniqueInvoiceNumber() {
        return (int) System.currentTimeMillis();
    }


    public List<Invoice> getInvoicesByCustomerId(int customerId) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM invoices WHERE customerID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Create an Invoice object for each row in the result set
                    Invoice invoice = new Invoice();
                    invoice.setInvoiceNumber(resultSet.getInt("InvoiceNumber"));
                    invoice.setInvoiceDate(resultSet.getString("InvoiceDate"));
                    invoice.setCustomerId(resultSet.getInt("CustomerId"));
                    invoice.setProductId(resultSet.getInt("ProductId"));
                    invoice.setUnitPrice(resultSet.getDouble("UnitPrice"));
                    invoice.setUnits(resultSet.getInt("Units"));
                    invoice.setTotalPrice(resultSet.getDouble("TotalPrice"));
                    invoice.setDiscount(resultSet.getDouble("Discount"));
                    invoice.setDiscountedTotalPrice(resultSet.getDouble("DiscountedTotalPrice"));

                    // Add the invoice to the list
                    invoices.add(invoice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return invoices;
    }

    // Additional methods if needed
}
