import model.Customer;
import model.Invoice;
import model.Product;
import service.CustomerService;
import service.InvoiceService;
import service.ProductService;
import util.DatabaseActivityLogger;
import java.util.List;
import java.util.Scanner;

// Package declaration and imports

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ProductService productService = new ProductService();
        CustomerService customerService = new CustomerService();
        InvoiceService invoiceService = new InvoiceService();

        DatabaseActivityLogger activityLogger = new DatabaseActivityLogger();

        boolean exit = false;

        while (!exit) {
            printMainMenu();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    handleProductMenu(scanner, productService, activityLogger);
                    break;
                case 2:
                    handleCustomerMenu(scanner, customerService, activityLogger);
                    break;
                case 3:
                    handleInvoiceMenu(scanner, productService, customerService, invoiceService, activityLogger);
                    break;
                case 4:
                    handleAdminMenu(scanner);
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Manage Products");
        System.out.println("2. Manage Customers");
        System.out.println("3. Invoice Generation");
        System.out.println("4. Admin Tasks");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleProductMenu(Scanner scanner, ProductService productService, DatabaseActivityLogger activityLogger) {
        boolean productMenuExit = false;

        while (!productMenuExit) {
            printProductMenu();

            int productChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (productChoice) {
                case 1:
                    addProduct(scanner, productService);
                    break;
                case 2:
                    updateProduct(scanner, productService, activityLogger);
                    break;
                case 3:
                    removeProduct(scanner, productService, activityLogger);
                    break;
                case 4:
                    searchProduct(scanner, productService);
                    break;
                case 5:
                    displayAllProducts(productService);
                    break;
                case 6:
                    productMenuExit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

            private static void printProductMenu() {
        System.out.println("\nProduct Menu:");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. Remove Product");
        System.out.println("4. Search Product");
        System.out.println("5. Display All Products");
        System.out.println("6. Return to Main Menu");
        System.out.print("Enter your choice: ");
    }
            private static void addProduct(Scanner scanner, ProductService productService) {
        System.out.println("\nEnter Product Details:");

        System.out.print("Product Name: ");
        String productName = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Purchase Price: ");
        double purchasePrice = scanner.nextDouble();

        System.out.print("Selling Price: ");
        double sellingPrice = scanner.nextDouble();

        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();

        // Create a new Product object with user input
        Product newProduct = new Product();
        newProduct.setProductName(productName);
        newProduct.setDescription(description);
        newProduct.setPurchasePrice(purchasePrice);
        newProduct.setSellingPrice(sellingPrice);
        newProduct.setQuantity(quantity);

        // Call the productService.addProduct method to add the product
        productService.addProduct(newProduct);

    }
            private static void updateProduct(Scanner scanner, ProductService productService, DatabaseActivityLogger activityLogger) {
                System.out.println("\nEnter Product ID to Update:");
                int productIdToUpdate = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                // Check if the product with the given ID exists
                Product existingProduct = productService.getProductById(productIdToUpdate);

                if (existingProduct != null) {
                    System.out.println("Enter Updated Product Details:");

                    System.out.print("Product Name: ");
                    String updatedProductName = scanner.nextLine();

                    System.out.print("Description: ");
                    String updatedDescription = scanner.nextLine();

                    System.out.print("Purchase Price: ");
                    double updatedPurchasePrice = scanner.nextDouble();

                    System.out.print("Selling Price: ");
                    double updatedSellingPrice = scanner.nextDouble();

                    System.out.print("Quantity: ");
                    int updatedQuantity = scanner.nextInt();

                    // Create a new Product object with updated details
                    Product updatedProduct = new Product();
                    updatedProduct.setProductId(productIdToUpdate);
                    updatedProduct.setProductName(updatedProductName);
                    updatedProduct.setDescription(updatedDescription);
                    updatedProduct.setPurchasePrice(updatedPurchasePrice);
                    updatedProduct.setSellingPrice(updatedSellingPrice);
                    updatedProduct.setQuantity(updatedQuantity);

                // Call the productService.updateProduct method to update the product
                productService.updateProduct(updatedProduct,0);

            } else {
                System.out.println("Product with ID " + productIdToUpdate + " does not exist.");
            }
        }
            private static void removeProduct(Scanner scanner, ProductService productService, DatabaseActivityLogger activityLogger) {
        System.out.println("\nEnter Product ID to Remove:");
        int productIdToRemove = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Check if the product with the given ID exists
        Product existingProduct = productService.getProductById(productIdToRemove);

        if (existingProduct != null) {
            // Call the productService.removeProduct method to remove the product
            productService.removeProduct(productIdToRemove);

        } else {
            System.out.println("Product with ID " + productIdToRemove + " does not exist.");
        }
    }
            private static void searchProduct(Scanner scanner, ProductService productService) {
        System.out.println("\nEnter Product ID to Search:");
        int productIdToSearch = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Call the productService.searchProduct method to search for the product
        Product searchedProduct = productService.searchProduct(productIdToSearch);

        if (searchedProduct != null) {
            System.out.println("\nProduct Details:");
            System.out.println("Product ID: " + searchedProduct.getProductId());
            System.out.println("Product Name: " + searchedProduct.getProductName());
            System.out.println("Description: " + searchedProduct.getDescription());
            System.out.println("Purchase Price: " + searchedProduct.getPurchasePrice());
            System.out.println("Selling Price: " + searchedProduct.getSellingPrice());
            System.out.println("Quantity: " + searchedProduct.getQuantity());
        } else {
            System.out.println("Product with ID " + productIdToSearch + " not found.");
        }
    }
            private static void displayAllProducts(ProductService productService) {
        System.out.println("\nAll Products:");

        // Call the productService.getAllProducts method to get all products
        List<Product> allProducts = productService.getAllProducts();

        if (allProducts.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : allProducts) {
                System.out.println("Product ID: " + product.getProductId());
                System.out.println("Product Name: " + product.getProductName());
                System.out.println("Description: " + product.getDescription());
                System.out.println("Purchase Price: " + product.getPurchasePrice());
                System.out.println("Selling Price: " + product.getSellingPrice());
                System.out.println("Quantity: " + product.getQuantity());
                System.out.println("------------------------------");
            }
        }
    }

    private static void handleCustomerMenu(Scanner scanner, CustomerService customerService, DatabaseActivityLogger activityLogger) {
        boolean customerMenuExit = false;

        while (!customerMenuExit) {
            printCustomerMenu();

            int customerChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (customerChoice) {
                case 1:
                    addCustomer(scanner, customerService, activityLogger);
                    break;
                case 2:
                    updateCustomer(scanner, customerService, activityLogger);
                    break;
                case 3:
                    removeCustomer(scanner, customerService, activityLogger);
                    break;
                case 4:
                    searchCustomer(scanner, customerService);
                    break;
                case 5:
                    displayAllCustomers(customerService);
                    break;
                case 6:
                    customerMenuExit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

            private static void printCustomerMenu() {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Add Customer");
            System.out.println("2. Update Customer");
            System.out.println("3. Remove Customer");
            System.out.println("4. Search Customer");
            System.out.println("5. Display All Customers");
            System.out.println("6. Return to Main Menu");
            System.out.print("Enter your choice: ");
            }
            private static void addCustomer(Scanner scanner, CustomerService customerService, DatabaseActivityLogger activityLogger) {
                System.out.println("\nEnter Customer Details:");

                System.out.print("Customer Name: ");
                String customerName = scanner.nextLine();

                System.out.print("Email: ");
                String email = scanner.nextLine();

                System.out.print("Address: ");
                String address = scanner.nextLine();

                System.out.print("Contact Number: ");
                String contactNumber = scanner.nextLine();

                System.out.print("Date of Birth (YYYY-MM-DD): ");
                String dateOfBirth = scanner.nextLine();

                System.out.print("Gender: ");
                String gender = scanner.nextLine();

                // Create a new Customer object with user input
                Customer newCustomer = new Customer();
                newCustomer.setCustomerName(customerName);
                newCustomer.setEmail(email);
                newCustomer.setAddress(address);
                newCustomer.setContactNumber(contactNumber);
                newCustomer.setDateOfBirth(dateOfBirth);
                newCustomer.setGender(gender);

                // Call the customerService.addCustomer method to add the customer
                customerService.addCustomer(newCustomer);

            }
            private static void updateCustomer(Scanner scanner, CustomerService customerService, DatabaseActivityLogger activityLogger) {
                System.out.println("\nEnter Customer ID to Update:");
                int customerIdToUpdate = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                // Check if the customer with the given ID exists
                Customer existingCustomer = customerService.getCustomerById(customerIdToUpdate);

                if (existingCustomer != null) {
                    System.out.println("\nEnter Updated Customer Details:");

                    System.out.print("Customer Name: ");
                    String updatedName = scanner.nextLine();

                    System.out.print("Email: ");
                    String updatedEmail = scanner.nextLine();

                    System.out.print("Address: ");
                    String updatedAddress = scanner.nextLine();

                    System.out.print("Contact Number: ");
                    String updatedContactNumber = scanner.nextLine();

                    System.out.print("Date of Birth (YYYY-MM-DD): ");
                    String updatedDateOfBirth = scanner.nextLine();

                    System.out.print("Gender: ");
                    String updatedGender = scanner.nextLine();

                    // Create a new Customer object with updated details
                    Customer updatedCustomer = new Customer();
                    updatedCustomer.setCustomerId(customerIdToUpdate);
                    updatedCustomer.setCustomerName(updatedName);
                    updatedCustomer.setEmail(updatedEmail);
                    updatedCustomer.setAddress(updatedAddress);
                    updatedCustomer.setContactNumber(updatedContactNumber);
                    updatedCustomer.setDateOfBirth(updatedDateOfBirth);
                    updatedCustomer.setGender(updatedGender);

                    // Call the customerService.updateCustomer method to update the customer
                    customerService.updateCustomer(updatedCustomer);

                } else {
                    System.out.println("Customer with ID " + customerIdToUpdate + " does not exist.");
                }
            }
            private static void removeCustomer(Scanner scanner, CustomerService customerService, DatabaseActivityLogger activityLogger) {
                System.out.println("\nEnter Customer ID to Remove:");
                int customerIdToRemove = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                // Check if the customer with the given ID exists
                Customer existingCustomer = customerService.getCustomerById(customerIdToRemove);

                if (existingCustomer != null) {
                    // Call the customerService.removeCustomer method to remove the customer
                    customerService.removeCustomer(customerIdToRemove);




                } else {
                    System.out.println("Customer with ID " + customerIdToRemove + " does not exist.");
                }
            }
            private static void searchCustomer(Scanner scanner, CustomerService customerService) {
                System.out.println("\nEnter Customer ID to Search:");
                int customerIdToSearch = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                // Call the customerService.searchCustomer method to search for the customer
                Customer foundCustomer = customerService.searchCustomer(customerIdToSearch);

                if (foundCustomer != null) {
                    System.out.println("Customer Found:");
                    System.out.println("\nCustomer Details:");
                    System.out.println("Customer ID: " + foundCustomer.getCustomerId());
                    System.out.println("Customer Name: " + foundCustomer.getCustomerName());
                    System.out.println("Address: " + foundCustomer.getAddress());
                    System.out.println("Email: " + foundCustomer.getEmail());
                    System.out.println("Contact Number: " + foundCustomer.getContactNumber());
                    System.out.println("Date Of Birth: " + foundCustomer.getDateOfBirth());
                    System.out.println("Gender: " + foundCustomer.getGender());
                } else {
                    System.out.println("Customer with ID " + customerIdToSearch + " not found.");
                }
            }
            private static void displayAllCustomers(CustomerService customerService) {
                System.out.println("\nAll Customers:");

                // Call the customerService.getAllCustomers method to get all customers
                List<Customer> allCustomers = customerService.getAllCustomers();

                if (allCustomers.isEmpty()) {
                    System.out.println("No customers found in the database.");
                } else {
                    for (Customer customer : allCustomers) {
                        System.out.println("\nCustomer Details:");
                        System.out.println("Customer ID: " + customer.getCustomerId());
                        System.out.println("Customer Name: " + customer.getCustomerName());
                        System.out.println("Address: " + customer.getAddress());
                        System.out.println("Email: " + customer.getEmail());
                        System.out.println("Contact Number: " + customer.getContactNumber());
                        System.out.println("Date Of Birth: " + customer.getDateOfBirth());
                        System.out.println("Gender: " + customer.getGender());
                    }
                }
    }

    private static void handleInvoiceMenu(Scanner scanner, ProductService productService, CustomerService customerService,
                                          InvoiceService invoiceService, DatabaseActivityLogger activityLogger) {
        boolean invoiceMenuExit = false;

        while (!invoiceMenuExit) {
            printInvoiceMenu();

            int invoiceChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (invoiceChoice) {
                case 1:
                    generateInvoice(scanner, productService, customerService, invoiceService, activityLogger);
                    break;
                case 2:
                    viewInvoices(scanner, invoiceService);
                    break;
                case 3:
                    invoiceMenuExit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        }
    }
            private static void printInvoiceMenu() {
        System.out.println("\nInvoice Menu:");
        System.out.println("1. Generate Invoice");
        System.out.println("2. View Invoices");
        System.out.println("3. Return to Main Menu");
        System.out.print("Enter your choice: ");
    }
            private static void generateInvoice(Scanner scanner, ProductService productService, CustomerService customerService,
                                        InvoiceService invoiceService, DatabaseActivityLogger activityLogger) {
                System.out.println("\nEnter Invoice Details:");
                System.out.print("Customer ID: ");
                int customerId = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                // Check if the customer with the given ID exists
                Customer customer = customerService.getCustomerById(customerId);

                if (customer != null) {
                    // Display available products
                    displayAllProducts(productService);
                    System.out.print("\nEnter Product ID: ");
                    int productId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    // Check if the product with the given ID exists
                    Product product = productService.getProductById(productId);
                    if (product != null) {
                        System.out.print("Units: ");
                        int units = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        // Call the invoiceService.generateInvoice method to generate the invoice
                        invoiceService.generateInvoice(customer, product, units);
                        System.out.println("Invoice generated successfully!");
                    } else {
                        System.out.println("Product with ID " + productId + " does not exist.");
                    }
                } else {
                    System.out.println("Customer with ID " + customerId + " does not exist.");
                }
            }
            private static void viewInvoices(Scanner scanner, InvoiceService invoiceService) {
                System.out.println("\nEnter Customer ID to View Invoices:");
                int customerId = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                // Call the invoiceService.viewInvoices method to view invoices for the given customer ID
                List<Invoice> invoices = invoiceService.getInvoicesByCustomerId(customerId);

                if (invoices.isEmpty()) {
                    System.out.println("No invoices found for the customer with ID " + customerId);
                } else {
                    System.out.println("Invoices for Customer ID " + customerId + ":");
                    for (Invoice invoice : invoices) {
                        System.out.println("Invoice Number : "+invoice.getInvoiceNumber());
                        System.out.println("Invoice Date : "+invoice.getInvoiceDate());
                        System.out.println("Customer Id : "+invoice.getCustomerId());
                        System.out.println("Product Id : "+invoice.getProductId());
                        System.out.println("UnitPrice : RS."+invoice.getUnitPrice());
                        System.out.println("UnitS : "+invoice.getUnits());
                        System.out.println("Total Price : RS."+invoice.getTotalPrice());
                        System.out.println("Discount : RS."+invoice.getDiscount());
                        System.out.println("Discounted Total Price : RS."+invoice.getDiscountedTotalPrice());


                    }
                }
            }


    private static void handleAdminMenu(Scanner scanner) {
        boolean adminMenuExit = false;

        while (!adminMenuExit) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View Database Activity Logs");
            System.out.println("2. Back to Main Menu");

            System.out.print("Enter your choice: ");
            int adminChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (adminChoice) {
                case 1:
                    // View database activity logs
                    //viewDatabaseActivityLogs();
                    break;
                case 2:
                    System.out.println("Returning to the Main Menu.");
                    adminMenuExit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

}
