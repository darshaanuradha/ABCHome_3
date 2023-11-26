package service;

// Package declaration and imports

import model.Customer;
import util.DatabaseActivityLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private static Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abchome_3", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Methods for business logic related to customers
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customerName, email, address, contactNumber, dateOfBirth, gender) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set parameters for the SQL query using customer details
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getContactNumber());
            statement.setString(5, customer.getDateOfBirth());
            statement.setString(6, customer.getGender());

            // Execute the SQL query to insert the customer into the database
            int rowsAffected = statement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                System.out.println("Customer added to the database successfully.");
                // Log the database insert activity
                DatabaseActivityLogger.logInsertActivity("Customers", getCustomerIDByEmail(customer.getEmail()));
            } else {
                System.out.println("Failed to add customer to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }

    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET customerName = ?, email = ?, address = ?, contactNumber = ?, dateOfBirth = ?, gender = ? WHERE customerID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getContactNumber());
            statement.setString(5, customer.getDateOfBirth());
            statement.setString(6, customer.getGender());
            statement.setInt(7, customer.getCustomerId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer updated in the database successfully.");
                // Log the database update activity
                DatabaseActivityLogger.logUpdateActivity("Customers", customer.getCustomerId());
            } else {
                System.out.println("Failed to update customer in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }

    public void removeCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE customerID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer removed from the database successfully.");
                // Log the database removal activity
                DatabaseActivityLogger.logRemoveActivity("Customers", customerId);
            } else {
                System.out.println("Failed to remove customer from the database. Customer ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }

    public Customer searchCustomer(int customerId) {
        String sql = "SELECT * FROM customers WHERE customerID = ?";
        Customer foundCustomer = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // If the result set is not empty, create a Customer object with the retrieved details
                    foundCustomer = new Customer();
                    foundCustomer.setCustomerId(resultSet.getInt("customerID"));
                    foundCustomer.setCustomerName(resultSet.getString("customerName"));
                    foundCustomer.setEmail(resultSet.getString("email"));
                    foundCustomer.setAddress(resultSet.getString("address"));
                    foundCustomer.setContactNumber(resultSet.getString("contactNumber"));
                    foundCustomer.setDateOfBirth(resultSet.getString("dateOfBirth"));
                    foundCustomer.setGender(resultSet.getString("gender"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return foundCustomer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> allCustomers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Create a Customer object for each row in the result set
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("customerID"));
                customer.setCustomerName(resultSet.getString("customerName"));
                customer.setEmail(resultSet.getString("email"));
                customer.setAddress(resultSet.getString("address"));
                customer.setContactNumber(resultSet.getString("contactNumber"));
                customer.setDateOfBirth(resultSet.getString("dateOfBirth"));
                customer.setGender(resultSet.getString("gender"));

                // Add the customer to the list
                allCustomers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return allCustomers;
    }



    // Additional methods if needed

    private static int getCustomerIDByEmail(String email) {
        String sql = "SELECT customerID FROM customers WHERE email = ?";
        int customerID = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    customerID = resultSet.getInt("customerID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return customerID;
    }
    // Logic for getting a customer by ID from the database
    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM customers WHERE customerID = ?";
        Customer customer = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // If the result set is not empty, create a Customer object with the retrieved details
                    customer = new Customer();
                    customer.setCustomerId(resultSet.getInt("customerID"));
                    customer.setCustomerName(resultSet.getString("customerName"));
                    customer.setEmail(resultSet.getString("email"));
                    customer.setAddress(resultSet.getString("address"));
                    customer.setContactNumber(resultSet.getString("contactNumber"));
                    customer.setDateOfBirth(resultSet.getString("dateOfBirth"));
                    customer.setGender(resultSet.getString("gender"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return customer;
    }

}

