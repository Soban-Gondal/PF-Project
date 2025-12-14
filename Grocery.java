import java.io.*;
import java.util.*;

public class Grocery{

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

    static final int MAX_SALES = 500;
    static SaleRecord[] sales = new SaleRecord[MAX_SALES];
    static int saleCount = 0;
    static Scanner sc = new Scanner(System.in);

    static String adminUser;
    static int adminPass;

    static String cashierUser;
    static int cashierPass;

    public static void main(String[] args) {

        loadAdmin();
        loadCashier();
        loadProducts();
        loadSales();
        
        mainMenu();
    }
    static void mainMenu() {
        int choice;

        do {
            System.out.println("\n===== GROCERY STORE SYSTEM =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Cashier Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            choice = safeInt();

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    cashierLogin();
                    break;
                case 3:
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }

        } while (choice != 3);
    }
    static void loadAdmin() {
        try (BufferedReader br = new BufferedReader(new FileReader("admin.txt"))) {
            String[] data = br.readLine().split(",");
            adminUser = data[0];
            adminPass = Integer.parseInt(data[1]);
        } catch (Exception e) {
        System.out.println("Admin file missing!");
        }
    }

    static void loadCashier() {
        try (BufferedReader br = new BufferedReader(new FileReader("cashier.txt"))) {
            String[] data = br.readLine().split(",");
            cashierUser = data[0];
            cashierPass = Integer.parseInt(data[1]);
        } catch (Exception e) {
            System.out.println("Cashier file missing!");
        }
    }

    static void saveAdmin() {
        try (PrintWriter pw = new PrintWriter("admin.txt")) {
            pw.println(adminUser + "," + adminPass);
        } catch (Exception e) {
            System.out.println("Error saving admin file!");
        }
    }

    static void saveCashier() {
        try (PrintWriter pw = new PrintWriter("cashier.txt")) {
            pw.println(cashierUser + "," + cashierPass);
        } catch (Exception e) {
            System.out.println("Error saving cashier file!");
        }
    }


    static void adminLogin() {

        System.out.println("\n===== ADMIN LOGIN =====");
        System.out.println("1. Login");
        System.out.println("2. Change Password");
        int choice = safeInt();

        switch (choice) {

            case 1 : {
                System.out.print("Enter Admin Username: ");
                String user = sc.nextLine();

                System.out.print("Enter 4-digit PIN: ");
                int pass = safeInt();

                if (user.equals(adminUser) && pass == adminPass) {
                    sc.nextLine();
                    adminMenu();
                } else {
                    System.out.println("Invalid admin details!");
                }
            }
                break;

            case 2 : {
                System.out.println("\n===== CHANGE ADMIN PASSWORD =====");
                System.out.print("Enter current PIN: ");
                int oldPin = safeInt();

                if (oldPin == adminPass) {

                    System.out.print("Enter new PIN: ");
                    int newPin = safeInt();

                    System.out.print("Confirm new PIN: ");
                    int confirmPin = safeInt();

                    if (newPin == confirmPin) {
                        adminPass = newPin;
                        saveAdmin();
                        System.out.println("Password changed successfully!");
                    } else {
                        System.out.println("New PINs do not match!");
                    }
                } else {
                    System.out.println("Incorrect current PIN!");
                }
            }
                break;

            default : System.out.println("Invalid choice!");
                break;
        }
    }

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
                case 1 : addProduct();
                    break;
                case 2 : viewProducts();
                    break;
                case 3 :updateProduct();
                    break;
                case 4 : deleteProduct();
                    break;
                case 5 : viewSalesReport();
                    break;
                case 6 : lowStockAlert();
                    break;
                case 7 : exportData();
                    break;
                case 8 : System.out.println("Returning to main menu...");
                    break;
                default : System.out.println("Invalid choice.");
                    break;
            }
        } while (choice != 8);
    }

    static void cashierLogin() {

        System.out.println("\n===== CASHIER LOGIN =====");
        System.out.println("1. Login. ");
        System.out.println("2. Change Password. ");

        int choice = safeInt();

        switch (choice) {

            case 1 : {
                System.out.print("Enter Cashier Username: ");
                String user = sc.nextLine();

                System.out.print("Enter 4-digit PIN: ");
                int pass = safeInt();

                if (user.equals(cashierUser) && pass == cashierPass) {
                    sc.nextLine();
                    cashierMenu();
                } else {
                    System.out.println("Invalid cashier details!");
                }
            }
                break;

            case 2 : {
                System.out.println("\n===== CHANGE CASHIER PASSWORD =====");
                System.out.print("Enter current PIN: ");
                int oldPin = safeInt();

                if (oldPin == cashierPass) {

                    System.out.print("Enter new PIN: ");
                    int newPin = safeInt();

                    System.out.print("Confirm new PIN: ");
                    int confirmPin = safeInt();

                    if (newPin == confirmPin) {
                        cashierPass = newPin;
                        saveCashier();
                        System.out.println("Password changed successfully!");
                    } else {
                        System.out.println("PINs do not match!");
                    }

                } else {
                    System.out.println("Incorrect current PIN!");
                }
            }
                break;

            default : System.out.println("Invalid choice!");
                break;
        }
    }

    static void cashierMenu() {

        int choice;

        do {
            System.out.println("\n===== CASHIER MENU =====");
            System.out.println("1. Generate Bill");
            System.out.println("2. View Product List");
            System.out.println("3. Search Product");
            System.out.println("4. Back.");
            System.out.print("Choose option: ");

            choice = safeInt();
            sc.nextLine();

            switch (choice) {
                case 1 : generateBill();
                    break;
                case 2 : viewProducts();
                    break;
                case 3 : searchProduct();
                    break;
                case 4 : System.out.println("Returning...");
                    break;
                default : System.out.println("Invalid choice.");
                    break;
            }

        } while (choice != 4);
    }

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


    static void viewProducts() {
        System.out.println("\n===== PRODUCT LIST =====");
        System.out.println("ID | Name | Price | Qty | Category");

        for (int i = 0; i < productCount; i++) {
            Product p = products[i];
            System.out.println(p.id + " | " + p.name + " | Rs." + p.price + " | " + p.quantity + " | " + p.category);
        }
    }

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

static void generateBill() {

    String billId = "INV" + (int)(Math.random() * 100000);
    Date date = new Date();
    double total = 0;

    System.out.println("       Grocery Store.     ");
    System.out.println("--------------------------------------------");
    System.out.println("Invoice");
    System.out.println("Inv #   : " + billId);
    System.out.println("Date    : " + date);
    System.out.println("Cashier : Rashid");
    System.out.println("--------------------------------------------");
    System.out.printf("%-18s %5s %8s%n", "Item", "Qty", "Total");
    System.out.println("--------------------------------------------");

    while (true) {
        System.out.print("Enter Product ID (0 to finish): ");
        int id = safeInt();
        if (id == 0) break;

        int idx = findProductIndex(id);
        if (idx == -1) {
            System.out.println("Product not found!");
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

        System.out.printf("%-18s %5d %8.2f%n", p.name, qty, cost);
    }

    System.out.println("--------------------------------------------");

    double tax = total * 0.05;
    double grandTotal = total + tax;

    System.out.printf("%-25s %8.2f%n", "Sub Total:", total);
    System.out.printf("%-25s %8.2f%n", "Tax (5%):", tax);
    System.out.printf("%-25s %8.2f%n", "Total:", grandTotal);

    System.out.println("--------------------------------------------");
    System.out.println("Cash Paid     : " + grandTotal);
    System.out.println("Cash Balance  : 0.00");
    System.out.println("Items         : Completed");
    System.out.println();
    System.out.println("      * Thank You! Come Again *");
    System.out.println("        Terms and conditions apply");
    System.out.println("--------------------------------------------");
    System.out.println("* " + billId + " *");

    // Save bill
    saveBillToFile(billId, "Total: " + grandTotal);

    SaleRecord sr = new SaleRecord();
    sr.billId = billId;
    sr.total = grandTotal;
    sr.date = date.toString();

    sales[saleCount] = sr;
    saleCount++;

    saveSales();
}

    static void saveBillToFile(String billId, String content) {
        try (PrintWriter pw = new PrintWriter(billId + ".txt")) {
            pw.println(content);
        } catch (Exception e) {
            System.out.println("Error saving bill!");
        }
    }

    static void viewSalesReport() {
        System.out.println("\n===== SALES REPORT =====");
        for (SaleRecord r : sales) {
            System.out.println(r.billId + " | Rs." + r.total + " | " + r.date);
        }
    }

    static void lowStockAlert() {
        System.out.println("\n===== LOW STOCK PRODUCTS (<5) =====");
        for (int i = 0; i < productCount; i++) {
            if (products[i].quantity < 5) {
                System.out.println(products[i].name + " | Qty:" + products[i].quantity);
            }
        }
    }

    static void exportData() {
        saveProducts();
        saveSales();
        System.out.println("Data exported successfully.");
    }

    static void loadProducts() {
        try (BufferedReader br = new BufferedReader(new FileReader("products.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] arr = line.split(",");

                Product p = new Product();
                p.id = Integer.parseInt(arr[0]);
                p.name = arr[1];
                p.price = Double.parseDouble(arr[2]);
                p.quantity = Integer.parseInt(arr[3]);
                p.category = arr[4];

                products[productCount++] = p;
            }
        } catch (Exception ignored) {}
    }

    static void saveProducts() {
        try (PrintWriter pw = new PrintWriter("products.txt")) {
            for (int i = 0; i < productCount; i++) {
                Product p = products[i];
                pw.println(p.id + "," + p.name + "," + p.price + "," + p.quantity + "," + p.category);
            }
        } catch (Exception ignored) {}
    }

    static void loadSales() {
        try (BufferedReader br = new BufferedReader(new FileReader("sales.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                SaleRecord sr = new SaleRecord();
                sr.billId = arr[0];
                sr.total = Double.parseDouble(arr[1]);
                sr.date = arr[2];
                
                sales[saleCount] = sr;
                saleCount++;
            }
        } catch (Exception ignored) {}
    }

    static void saveSales() {
        try (PrintWriter pw = new PrintWriter("sales.txt")) {
            for (SaleRecord r : sales) {
                pw.println(r.billId + "," + r.total + "," + r.date);
            }
        } catch (Exception ignored) {}
    }

    static int safeInt() {
    while (true) {
        try {
            String input = sc.nextLine();

            if (input == null || input.trim().isEmpty()) {
                System.out.print("Input cannot be empty. Try again: ");
                continue;
            }

            return Integer.parseInt(input.trim());

        } catch (NumberFormatException e) {
            System.out.print("Invalid number. Enter again: ");
        } catch (Exception e) {
            System.out.println("\nInput interrupted. Returning to menu.");
            return -1;
        }
      }
    }
    static double safeDouble() {
    while (true) {
        try {
            String input = sc.nextLine();

            if (input == null || input.trim().isEmpty()) {
                System.out.print("Input cannot be empty. Try again: ");
                continue;
            }

            return Double.parseDouble(input.trim());

        } catch (NumberFormatException e) {
            System.out.print("Invalid amount. Enter again: ");
        } catch (Exception e) {
            System.out.println("\nInput interrupted. Returning to Menu.");
            return 0;
        }
      }
  }

 static int getUniqueProductId() {
    while (true) {
        int id = safeInt();

        if (id == -1) return -1;

        if (findProductIndex(id) == -1)
            return id;

        System.out.print("ID already exists. Enter unique ID: ");
    }
}

    static int findProductIndex(int id) {
        for (int i = 0; i < productCount; i++)
            if (products[i].id == id)
                return i;
        return -1;
    }
}






















