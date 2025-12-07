import java.io.*;
import java.util.*;

public class Grocery {

    // ------------------ Data Structures ------------------
    static class Product {
        int id;
        String name;
        double price;
        int quantity;
        String category;
    }

    static class SaleRecord {
        String billId;
        double total;
        String date;
    }

    static final int MAX_PRODUCTS = 500;
    static Product[] products = new Product[MAX_PRODUCTS];
    static int productCount = 0;

    static List<SaleRecord> sales = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    static final String ADMIN_USER = "Admin";
    static int ADMIN_PASS = 1234;

    static final String CASHIER_USER = "Cashier";
    static int CASHIER_PASS = 1111;

    // ------------------------------------------------------
    public static void main(String[] args) {

        // Load saved data
        loadProducts();
        loadSales();

        int choice;
        do {
            System.out.println("\n===== GROCERY STORE SYSTEM =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Cashier Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = safeInt();

            switch (choice) {
                case 1 -> adminLogin();
                case 2 -> cashierLogin();
                case 3 -> System.out.println("Exiting system. Goodbye!");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 3);
    }
    // ------------------------------------------------------
    // ADMIN LOGIN
    // ------------------------------------------------------
    static void adminLogin() {

        System.out.println("\n===== ADMIN LOGIN =====");
        System.out.println("1. Login");
        System.out.println("2. Change Password");
        int choice = safeInt();

        switch (choice) {

            case 1 -> {
                System.out.print("Enter Admin Username: ");
                String user = sc.next();

                System.out.print("Enter 4-digit PIN: ");
                int pass = safeInt();

                if (user.equals(ADMIN_USER) && pass == ADMIN_PASS) {
                    sc.nextLine();
                    adminMenu();
                } else {
                    System.out.println("Invalid admin details!");
                }
            }

            case 2 -> {
                System.out.println("\n===== CHANGE ADMIN PASSWORD =====");
                System.out.print("Enter current PIN: ");
                int oldPin = safeInt();

                if (oldPin == ADMIN_PASS) {

                    System.out.print("Enter new PIN: ");
                    int newPin = safeInt();

                    System.out.print("Confirm new PIN: ");
                    int confirmPin = safeInt();

                    if (newPin == confirmPin) {
                        ADMIN_PASS = newPin;
                        System.out.println("Password changed successfully!");
                    } else {
                        System.out.println("New PINs do not match!");
                    }
                } else {
                    System.out.println("Incorrect current PIN!");
                }
            }

            default -> System.out.println("Invalid choice!");
        }
    }

    // ------------------------------------------------------
    // ADMIN MENU
    // ------------------------------------------------------
    static void adminMenu() {

        int choice;
        do {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. View Sales Report");
            System.out.println("6. Low Stock Alert");
            System.out.println("7. Export Data");
            System.out.println("8. Back to Main Menu");
            System.out.print("Choose option: ");

            choice = safeInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewProducts();
                case 3 -> updateProduct();
                case 4 -> deleteProduct();
                case 5 -> viewSalesReport();
                case 6 -> lowStockAlert();
                case 7 -> exportData();
                case 8 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 8);
    }

    // ------------------------------------------------------
    // CASHIER LOGIN
    // ------------------------------------------------------
    static void cashierLogin() {

        System.out.println("\n===== CASHIER LOGIN =====");
        System.out.println("1. Login");
        System.out.println("2. Change Password");

        int choice = safeInt();

        switch (choice) {

            case 1 -> {
                System.out.print("Enter Cashier Username: ");
                String user = sc.next();

                System.out.print("Enter 4-digit PIN: ");
                int pass = safeInt();

                if (user.equals(CASHIER_USER) && pass == CASHIER_PASS) {
                    sc.nextLine();
                    cashierMenu();
                } else {
                    System.out.println("Invalid cashier details!");
                }
            }

            case 2 -> {
                System.out.println("\n===== CHANGE CASHIER PASSWORD =====");
                System.out.print("Enter current PIN: ");
                int oldPin = safeInt();

                if (oldPin == CASHIER_PASS) {

                    System.out.print("Enter new PIN: ");
                    int newPin = safeInt();

                    System.out.print("Confirm new PIN: ");
                    int confirmPin = safeInt();

                    if (newPin == confirmPin) {
                        CASHIER_PASS = newPin;
                        System.out.println("Password changed successfully!");
                    } else {
                        System.out.println("PINs do not match!");
                    }

                } else {
                    System.out.println("Incorrect current PIN!");
                }
            }

            default -> System.out.println("Invalid choice!");
        }
    }

    // ------------------------------------------------------
    // CASHIER MENU
    // ------------------------------------------------------
    static void cashierMenu() {

        int choice;

        do {
            System.out.println("\n===== CASHIER MENU =====");
            System.out.println("1. Generate Bill");
            System.out.println("2. View Product List");
            System.out.println("3. Search Product");
            System.out.println("4. Back");
            System.out.print("Choose option: ");

            choice = safeInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> generateBill();
                case 2 -> viewProducts();
                case 3 -> searchProduct();
                case 4 -> System.out.println("Returning...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 4);
    }
    // ------------------------------------------------------
    // ADD PRODUCT
    // ------------------------------------------------------
    static void addProduct() {
        Product p = new Product();

        System.out.print("Enter Product ID: ");
        p.id = getUniqueProductId();

        System.out.print("Enter Product Name: ");
        p.name = sc.nextLine();

        System.out.print("Enter Price: ");
        p.price = safeDouble();

        System.out.print("Enter Quantity: ");
        p.quantity = safeInt();

        System.out.print("Enter Category: ");
        p.category = sc.nextLine();

        products[productCount++] = p;
        System.out.println("Product added successfully.");
    }

    // ------------------------------------------------------
    static void viewProducts() {
        System.out.println("\n===== PRODUCT LIST =====");
        System.out.println("ID | Name | Price | Qty | Category");

        for (int i = 0; i < productCount; i++) {
            Product p = products[i];
            System.out.println(p.id + " | " + p.name + " | Rs." + p.price + " | " + p.quantity + " | " + p.category);
        }
    }

    // ------------------------------------------------------
    static void updateProduct() {
        System.out.print("Enter Product ID to update: ");
        int id = safeInt();
        sc.nextLine();

        int idx = findProductIndex(id);

        if (idx == -1) {
            System.out.println("Product not found.");
            return;
        }

        Product p = products[idx];

        System.out.print("New Name: ");
        p.name = sc.nextLine();

        System.out.print("New Price: ");
        p.price = safeDouble();

        System.out.print("New Quantity: ");
        p.quantity = safeInt();

        System.out.print("New Category: ");
        p.category = sc.nextLine();

        System.out.println("Product updated successfully.");
    }

    // ------------------------------------------------------
    static void deleteProduct() {
        System.out.print("Enter Product ID to delete: ");
        int id = safeInt();

        int idx = findProductIndex(id);

        if (idx == -1) {
            System.out.println("Product not found.");
            return;
        }

        for (int i = idx; i < productCount - 1; i++)
            products[i] = products[i + 1];

        productCount--;

        System.out.println("Product deleted.");
    }

    // ------------------------------------------------------
    // SEARCH PRODUCT
    // ------------------------------------------------------
    static void searchProduct() {
        System.out.print("Enter product name or ID: ");
        String input = sc.nextLine().toLowerCase();

        for (int i = 0; i < productCount; i++) {
            Product p = products[i];
            if (String.valueOf(p.id).equals(input) || p.name.toLowerCase().contains(input)) {
                System.out.println(p.id + " | " + p.name + " | Rs." + p.price + " | Qty: " + p.quantity);
                return;
            }
        }
        System.out.println("Product not found.");
    }

    // ------------------------------------------------------
    // BILL GENERATION WITH FILE HANDLING
    // ------------------------------------------------------
    static void generateBill() {

        String billId = "BILL" + System.currentTimeMillis();
        double total = 0;

        StringBuilder billContent = new StringBuilder();
        billContent.append("===== GROCERY BILL =====\n");
        billContent.append("Bill ID: ").append(billId).append("\n");
        billContent.append("Date: ").append(new Date()).append("\n\n");
        billContent.append("Item | Qty | Unit Price | Total\n");

        while (true) {
            System.out.print("Enter Product ID (0 to finish): ");
            int id = safeInt();

            if (id == 0) break;

            int idx = findProductIndex(id);
            if (idx == -1) {
                System.out.println("Product not found.");
                continue;
            }

            Product p = products[idx];

            System.out.print("Enter Quantity: ");
            int qty = safeInt();

            if (qty > p.quantity) {
                System.out.println("Not enough stock!");
                continue;
            }

            double cost = qty * p.price;
            total += cost;
            p.quantity -= qty;

            billContent.append(p.name).append(" | ").append(qty).append(" | ")
                    .append(p.price).append(" | ").append(cost).append("\n");

            System.out.println(qty + " x " + p.name + " = Rs." + cost);
        }

        // TAX 5%
        double tax = total * 0.05;
        total += tax;

        billContent.append("\nTax (5%): ").append(tax).append("\n");
        billContent.append("Total Amount: Rs. ").append(total).append("\n");

        // Save bill to file
        saveBillToFile(billId, billContent.toString());

        // Save record in memory + file
        SaleRecord sr = new SaleRecord();
        sr.billId = billId;
        sr.total = total;
        sr.date = new Date().toString();
        sales.add(sr);

        saveSales();

        System.out.println("\nBill Generated Successfully!");
        System.out.println("Saved as: " + billId + ".txt");
    }

    // ------------------------------------------------------
    static void saveBillToFile(String billId, String content) {
        try (PrintWriter pw = new PrintWriter(billId + ".txt")) {
            pw.println(content);
        } catch (Exception e) {
            System.out.println("Error saving bill!");
        }
    }


