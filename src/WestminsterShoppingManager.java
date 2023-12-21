import java.io.*;
import java.util.*;

// Class: WestminsterShoppingManager
public class WestminsterShoppingManager implements ShoppingManager {
    private static final int MAX_PRODUCTS = 50;
    private static ArrayList<Product> productList;
    private static ArrayList<User> userList;
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    private Scanner scanner;

    // Constructor
    public WestminsterShoppingManager() {
        WestminsterShoppingManager.productList = new ArrayList<>();
        WestminsterShoppingManager.userList = new ArrayList<>();

        // Clothing clothing1 = new Clothing("C001", "T-Shirt", 10, 20.0, "M", "Red");
        // Clothing clothing2 = new Clothing("C002", "Jeans", 5, 50.0, "L", "Blue");
        // Clothing clothing3 = new Clothing("C003", "Shirt", 15, 30.0, "S", "White");
        // Electronics electronics1 = new Electronics("E001", "Laptop", 10, 1000.0,
        // "Dell", 12);
        // Electronics electronics2 = new Electronics("E002", "Mobile Phone", 20, 500.0,
        // "Samsung", 6);
        // Electronics electronics3 = new Electronics("E003", "TV", 5, 2000.0, "Sony",
        // 24);

        // productList.add(clothing1);
        // productList.add(clothing2);
        // productList.add(clothing3);
        // productList.add(electronics1);
        // productList.add(electronics2);
        // productList.add(electronics3);

        // WestminsterShoppingManager.userList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        // Load products and users from file at application startup
        loadProductsFromFile();
        loadUsersFromFile();
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public static ArrayList<Product> getProductList() {
        return productList;
    }

    public static User getUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static Product getProduct(String productId) {
        Product product = null;
        for (Product p : productList) {
            if (p.getProductId().equals(productId)) {
                product = p;
                break;
            }

        }
        return product;
    }

    // Implementation of interface methods
    @Override
    public void addProduct(Product product) {
        for (Product existingProduct : productList) {
            if (existingProduct.getProductId().equals(product.getProductId())) {
                System.out.println("Product with ID " + product.getProductId()
                        + " already exists. Cannot add duplicate products.");
                return;
            }
        }

        if (productList.size() < MAX_PRODUCTS) {
            productList.add(product);
            System.out.println(product.getProductName() + " added to the system.");
        } else {
            System.out.println("Maximum limit of products reached. Cannot add more products.");
        }
    }

    @Override
    public void removeProduct(Product product) {
        if (productList.remove(product)) {
            System.out.println(product.getProductName() + " removed from the system.");
            System.out.println("Total number of products left in the system: " + productList.size());
            // Save products to file after each removal
            saveProductsToFile();
        } else {
            System.out.println("Product not found in the system.");
        }
    }

    @Override
    public double calculateTotalCost() {
        double totalCost = 0.0;
        for (Product product : productList) {
            totalCost += product.getPrice();
        }
        return totalCost;
    }

    @Override
    public void displayProducts() {
        System.out.println("Products in the system:");
        for (Product product : productList) {
            System.out.println("----------------------------------------");
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Price: $" + product.getPrice());
            System.out.println("Quantity: " + product.getProductQty());
            if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                System.out.println("Type: Clothing");
                System.out.println("Size: " + clothing.getSize());
                System.out.println("Color: " + clothing.getColour());
            } else if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                System.out.println("Type: Electronics");
                System.out.println("Brand: " + electronics.getBrand());
                System.out.println("Warranty Period: " + electronics.getWarrantyPeriod() + " months");
            }
            System.out.println("----------------------------------------");
        }
    }

    // Method to display the console menu
    public void displayMenu() {
        int choice;
        do {
            System.out.println("--------------------------------------------------");
            System.out.println("--------Westminster Shopping Manager Menu---------");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Add a new product to the system ");
            System.out.println("2. Delete a product from the system ");
            System.out.println("3. Print the list of products in the system ");
            System.out.println("4. Save products to file ");
            System.out.println("5. Open GUI ");
            System.out.println("6. Exit ");
            System.out.println("--------------------------------------------------");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        addNewProduct();
                        break;
                    case 2:
                        deleteProduct();
                        break;
                    case 3:
                        printProductList();
                        break;
                    case 4:
                        saveProductsToFile();
                        break;
                    case 5:
                        openGUI();
                        break;
                    case 6:
                        System.out.println("Exiting the system manager.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear the scanner
                choice = 0; // reset choice
            }
        } while (choice != 6);
    }

    // Method to add a new product to the system based on user input
    private void addNewProduct() {
        try {
            System.out.println("\nAdding a new product to the system:");
            System.out.println("1. Add Electronics");
            System.out.println("2. Add Clothing");
            System.out.print("Enter your choice: ");
            int productType = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (productType) {
                case 1:
                    addElectronics();
                    break;
                case 2:
                    addClothing();
                    break;
                default:
                    System.out.println("Invalid choice. Returning to the main menu.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct type for each field.");
            scanner.nextLine(); // clear the scanner
        }
    }

    private void addElectronics() {
        try {
            System.out.print("Enter product ID: ");
            String productId = scanner.nextLine();
            System.out.print("Enter product name: ");
            String productName = scanner.nextLine();
            System.out.print("Enter price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter product quantity: ");
            int productQty = scanner.nextInt();
            System.out.print("Enter brand: ");
            String brand = scanner.nextLine();
            System.out.print("Enter warranty period (in months): ");
            int warrantyPeriod = scanner.nextInt();

            Electronics electronics = new Electronics(productId, productName, productQty, price, brand, warrantyPeriod);
            addProduct(electronics);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct type for each field.");
            scanner.nextLine(); // clear the scanner
        }
    }

    private void addClothing() {
        try {
            System.out.print("Enter product ID: ");
            String productId = scanner.nextLine();
            System.out.print("Enter product name: ");
            String productName = scanner.nextLine();
            System.out.print("Enter price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter product quantity: ");
            int productQty = scanner.nextInt();
            System.out.print("Enter size: ");
            String size = scanner.nextLine();
            System.out.print("Enter color: ");
            String color = scanner.nextLine();

            Clothing clothing = new Clothing(productId, productName, productQty, price, size, color);
            addProduct(clothing);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct type for each field.");
            scanner.nextLine(); // clear the scanner
        }
    }

    // Method to delete a product from the system based on user input
    private void deleteProduct() {
        System.out.print("\nEnter the product ID to delete: ");
        String productIdToDelete = scanner.nextLine();

        // Find the product with the given ID
        Product productToDelete = null;
        for (Product product : productList) {
            if (product.getProductId().equals(productIdToDelete)) {
                productToDelete = product;
                break;
            }
        }

        if (productToDelete != null) {
            // Determine if the product is electronics or clothing
            String productType = (productToDelete instanceof Electronics) ? "Electronics" : "Clothing";

            // Remove the product from the system
            removeProduct(productToDelete);

            // Display information about the deleted product and the total number of
            // products left
            System.out.println(productType + " with ID " + productIdToDelete + " has been deleted from the system.");
            System.out.println("Total number of products left in the system: " + productList.size());
        } else {
            System.out.println("Product with ID " + productIdToDelete + " not found in the system.");
        }
    }

    // Method to print the list of products alphabetically by product ID
    private void printProductList() {
        Collections.sort(productList, Comparator.comparing(Product::getProductId));
        displayProducts();
    }

    // Method to save the list of products to a file
    static void saveProductsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.txt"))) {
            oos.writeObject(productList);
            System.out.println("Products saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving products to file: " + e.getMessage());
        }
    }

    // Method to load the list of products from a file at application startup
    @SuppressWarnings("unchecked")
    private void loadProductsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("products.txt"))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                productList = (ArrayList<Product>) obj;
                System.out.println("Products loaded from file successfully.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading products from file: " + e.getMessage());
        }
    }

    // Method to save the list of user to a file
    static void saveUsersToFile() {
        ArrayList<User> userList = WestminsterShoppingManager.getUserList();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.txt"))) {
            oos.writeObject(userList);
            System.out.println("Users saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    // Method to load the list of users from a file at application startup
    @SuppressWarnings("unchecked")
    private void loadUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.txt"))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                userList = (ArrayList<User>) obj;
                System.out.println("Users loaded from file successfully.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
    }

    // Method to open the GUI
    private void openGUI() {
        // Create an instance of ShoppingGUI
        new LoginGUI();
    }

}
