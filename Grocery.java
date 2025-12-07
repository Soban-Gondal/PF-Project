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

