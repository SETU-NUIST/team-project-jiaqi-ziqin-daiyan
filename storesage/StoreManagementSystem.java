import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

public class StoreManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Store store = new Store();

    public static void main(String[] args) {
        System.out.println("===== Store Commodity Management System =====");
        // Loop to display the menu until the user chooses to exit.
        initDefaultProducts();
        while (true) {
            showMenu();
            int choice = getValidIntInput("Please enter your choice.（1-8）：", 1, 8);
            handleMenuChoice(choice);
            if (choice == 8) {
                break;
            }
            // Pause after each operation to allow the user to view the results.
            System.out.println("Press Enter to continue....");
            scanner.nextLine();
        }
        System.out.println("👋 Thank you for using. Goodbye！");
        scanner.close();
    }

    // 显示操作菜单
    private static void showMenu() {
        System.out.println("===== Function Menu =====");
        System.out.println("1.Add a New Product ");
        System.out.println("2. View All Products");
        System.out.println("3. Find the Cheapest Product");
        System.out.println("4. View On-sale Products");
        System.out.println("5. Calculate the Average Price of Products");
        System.out.println("6.View products higher than the specified price ");
        System.out.println("7. Modify Product Information");
        System.out.println("8. Back to System");
    }

    private static void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                addProductByInput();
                break;
            case 2:
                System.out.println(store.listProducts());
                break;
            case 3:
                findCheapestProduct();
                break;
            case 4:
                System.out.println(store.listCurrentProducts());
                break;
            case 5:
                calculateAveragePrice();
                break;
            case 6:
                listProductsAbovePrice();
                break;
            case 7:
                modifyProduct();
                break;
            case 8:
                break; // The exit logic is handled in the main method
            default:
                System.out.println("🚫 Invalid selection, please re-enter!");
        }
    }

    private static void initDefaultProducts() {
        store.add(new Product("Apple", 2.99, true));

        store.add(new Product("Banana", 3.99, true));

        store.add(new Product("Orange", 4.59, true));

        store.add(new Product("Milk", 5.99, true));

        store.add(new Product("bread", 6.99, true));
    }


    // Enter product information and add it
    private static void addProductByInput() {
        System.out.println("===== Add a New Product =====");
        System.out.print("Please enter the product name：");
        String name = scanner.nextLine();
        while (name.isEmpty()) {
            System.out.print("🚫 Product name cannot be empty, please re-enter：");
            name = scanner.nextLine();
        }

        // Enter the commodity price
        double price = getValidDoubleInput("Please enter the unit price of the product（元）：", 0.0, 10000.0);

        // Check if the item is on sale.
        boolean isInLine = getValidBooleanInput("Is the product on sale?？（y/n）：");

        // Add products
        Product product = new Product(name, price, isInLine);
        store.add(product);
        System.out.println("✅Product added successfully. ！");
    }

    // Find the cheapest products.
    private static void findCheapestProduct() {
        Product cheapest = store.cheapestProduct();
        if (cheapest == null) {
            System.out.println("📦 No products in the store, cannot find the cheapest product!");
        } else {
            System.out.println("🏷️ The cheapest product:");
            System.out.println(cheapest);
        }
    }

    // Calculate the average price of goods
    private static void calculateAveragePrice() {
        Double average = store.averageProductPrice();
        if (average == null) {
            System.out.println("📦No products in the store, cannot calculate average price!\n ");
        } else {
            System.out.printf("📊 Average price: " + average + " yuan");
        }
    }

    // View products priced higher than the specified price.
    private static void listProductsAbovePrice() {
        System.out.print("Please enter the price threshold (yuan)：");
        double price = getValidDoubleInput("Please enter the price threshold (yuan)：", 0.0, 1000.0);
        System.out.println(store.listProductsAboveAPrice(price));
    }

    // Get a legal integer input
    private static int getValidIntInput(String prompt, int min, int max) {
        int input = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.printf("🚫The input is out of range. Please enter again. ：", min, max);
                }
            } else {
                System.out.print("🚫The input is not an integer. Please enter again. ：");
                scanner.next(); //Clear invalid input
            }
        }
        scanner.nextLine(); //Absorb carriage return characters
        return input;
    }

    //Enter new products
    private static double getValidDoubleInput(String prompt, double min, double max) {
        double input = 0.0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                input = scanner.nextDouble();
                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.printf("🚫The input is out of range. Please enter again. ：", min, max);
                }
            } else {
                System.out.print("🚫The input is not a number. Please enter again.\n ：");
                scanner.next(); //Clear invalid input
            }
        }
        scanner.nextLine(); // Absorb carriage return
        return input;
    }


    private static boolean getValidBooleanInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                return true;
            } else if (input.equals("no")) {
                return false;
            } else {
                System.out.println("🚫The input is invalid. Please enter yes or no. ");
            }
        }
    }


    private static void modifyProduct() {
        System.out.println("\n===== Modify product information =====");

        if (store.getProductCount() == 0) {
            System.out.println("📦 There are no products available for modification at the moment.！");
            return;
        }

        System.out.println(store.listProducts());

        int productNo = getValidIntInput("Please enter the product number.（1-" + store.getProductCount() + "）：", 1, store.getProductCount());
        int index = productNo - 1; // Convert to index
        Product target = store.getProductByIndex(index);

        double newPrice = getValidDoubleInput("Please enter the new price.（元）：", 0, 10000);
        target.setUnitCost(newPrice); //Directly call the setter

        boolean newStatus = getValidBooleanInput("Is it on sale?（yes/no）：");
        target.setInCurrentProductLine(newStatus); //Directly call the setter


        System.out.println("✅Modification successful! After modification ：" + target);
    }

    // 【Simplified】Get the quantity of goods (directly call the Store method to replace string parsing)
    private static int countProducts() {
        return store.getProductCount();
    }

    //[【Simplified】Get products by index (directly call the Store method instead of using reflection)]
    private static Product getProductByIndex(int index) {
        return store.getProductByIndex(index);
    }
}