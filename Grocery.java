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


