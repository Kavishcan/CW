import java.io.*;
import java.util.*;

// Class: WestminsterShoppingManager
public class WestminsterShoppingManager implements ShoppingManager {
    private static final int MAX_PRODUCTS = 50;
    private static ArrayList<Product>  productList;
    private static ArrayList<User> userList;
    private static User currentUser;
    private Scanner scanner;

    // Constructor
    public WestminsterShoppingManager() {
        WestminsterShoppingManager.productList = new ArrayList<>();
        WestminsterShoppingManager.userList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
         loadUsersFromFile();
        loadFromFile();
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

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }


    // Implementation of interface methods
    @Override
    public void addProduct(Product product) {
        // Check if a product with the same ID already exists
        for (Product existingProduct : productList) {
            if (existingProduct.getProductId().equals(product.getProductId())) {
                System.out.println("A product with ID " + product.getProductId() + " already exists. Cannot add the product.");
                return;
            }
        }

        // Check if the product list has reached its maximum size
        if (productList.size() < MAX_PRODUCTS) {
            productList.add(product);
            System.out.println(product.getProductName() + " added to the system.");
            saveToFile();
        } else {
            System.out.println("Maximum limit of products reached. Cannot add more products.");
        }
    }

    @Override
    public void removeProduct(String productIdToDelete) {
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
            if (productList.remove(productToDelete)) {
                System.out.println(productToDelete.getProductName() + " removed from the system.");
                System.out.println("Total number of products left in the system: " + productList.size());
                // Save products to file after each removal
                saveToFile();
            } else {
                System.out.println("Product not found in the system.");
            }

            // Display information about the deleted product and the total number of
            // products left
            System.out.println(productType + " with ID " + productIdToDelete + " has been deleted from the system.");
            System.out.println("Total number of products left in the system: " + productList.size());
        } else {
            System.out.println("Product with ID " + productIdToDelete + " not found in the system.");
        }
    }

    @Override
    public void displayProducts() {
        Collections.sort(productList, Comparator.comparing(Product::getProductId));
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

    // Method to update the items quantity with the productID
    @Override
    public void updateProductQty(String productIdToUpdate, int newQty) {
        boolean productFound = false;
        for (Product product : productList) {
            if (product.getProductId().equals(productIdToUpdate)) {
                product.setProductQty(newQty);
                System.out.println("Product with ID " + productIdToUpdate + " has been updated.");
                productFound = true;
                saveToFile();
                break;
            }
        }
        if (!productFound) {
            System.out.println("Product with ID " + productIdToUpdate + " not found in the system.");
        }
    }

    // Method to save the list of products to a file
    @Override
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.txt"))) {
            oos.writeObject(productList);
            System.out.println("Products saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving products to file: " + e.getMessage());
        }
    }

    // Method to load the list of products from a file at application startup
    @Override
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("products.txt"))) {
            Object obj = ois.readObject();
            if (obj != null && obj instanceof List) {
                productList = (ArrayList<Product>) obj;
                System.out.println("Products loaded from file successfully.");
            } else {
                productList = new ArrayList<>(); // Initialize productList if obj is null
                System.out.println("No products found in file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading products from file: " + e.getMessage());
        }
    }

    // Method to open the GUI
    @Override
    public void openGUI() {
        // Create an instance of LoginGUI
        new LoginGUI();
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
            System.out.println("4. Update the quantity of a product ");
            System.out.println("5. Save products to file ");
            System.out.println("6. Load products from file ");
            System.out.println("7. Open GUI ");
            System.out.println("8. Exit ");
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
                        String productIdToDelete = "";

                        while (true) {
                            try {
                                System.out.print("\nEnter the product ID to delete: ");
                                productIdToDelete = scanner.nextLine();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid product ID:");
                                scanner.nextLine(); // clear the scanner
                            }
                        }
                        removeProduct(productIdToDelete);
                        break;
                    case 3:
                        displayProducts();
                        break;
                    case 4:
                        String productIdToUpdate = "";
                        int newQty = 0;

                        while (true) {
                            try {
                                System.out.print("\nEnter the product ID to update: ");
                                productIdToUpdate = scanner.nextLine();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid product ID to update:");
                                scanner.nextLine(); // clear the scanner
                            }
                        }

                        while (true) {
                            try {
                                System.out.print("Enter the new quantity: ");
                                newQty = scanner.nextInt();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid quantity:");
                                scanner.nextLine(); // clear the scanner
                            }
                        }
                        updateProductQty(productIdToUpdate, newQty);
                        break;
                    case 5:
                        saveToFile();
                        break;
                    case 6:
                        loadFromFile();
                        break;
                    case 7:
                        openGUI();
                        break;
                    case 8:
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
        } while (choice != 8);
    }

    // Method to add a new product to the system based on user input
    private void addNewProduct() {

        boolean validInput = false;
        while (!validInput) {

            try {
                System.out.println("\nAdding a new product to the system:");
                System.out.println("1. Add Electronics");
                System.out.println("2. Add Clothing");
                System.out.println("3. Back to main menu");
                System.out.print("Enter your choice: ");
                int productType = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character


                String productName = "";
                double price = 0;
                int productQty = 0;


                System.out.print("Enter product name: ");
                productName = scanner.nextLine();

                while (true) {
                    try {
                        System.out.print("Enter price: ");
                        price = scanner.nextDouble();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid price.");
                        scanner.nextLine(); // clear the scanner
                    }
                }

                while (true) {
                    try {
                        System.out.print("Enter product quantity: ");
                        productQty = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid product quantity.");
                        scanner.nextLine(); // clear the scanner
                    }
                }

                switch (productType) {
                    case 1:
                        addElectronics(productName, price, productQty);
                        break;
                    case 2:
                        addClothing(productName, price, productQty);
                        break;
                    case 3:
                        displayMenu();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter the correct option.");
                }
                validInput = true;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter the correct type for each field.");
                scanner.nextLine(); // clear the scanner
            }
        }
    }

    // Method to generate a unique product ID
    String generateProductId(String productType) {
        int counter = productList.size() + 1;
        String productId = "";

        while (true) {
            if (productType.equals("Electronics")) {
                productId = "E" + String.format("%03d", counter);
            } else if (productType.equals("Clothing")) {
                productId = "C" + String.format("%03d", counter);
            }

            boolean idExists = false;
            for (Product product : productList) {
                if (product.getProductId().equals(productId)) {
                    idExists = true;
                    break;
                }
            }

            if (!idExists) {
                break;
            }

            counter++;
        }

        return productId;
    }

    // Method to add electronics to the system
    private void addElectronics(String productName, double price ,int productQty) {
        String productId = generateProductId("Electronics");
        String brand = "";
        int warrantyPeriod = 0;

        System.out.print("Enter brand: ");
        brand = scanner.nextLine();

        while (true) {
            try {
                System.out.print("Enter warranty period (in months): ");
                warrantyPeriod = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid warranty period.");
                scanner.nextLine(); // clear the scanner
            }
        }

        Electronics electronics = new Electronics(productId, productName, productQty, price, brand, warrantyPeriod);
        addProduct(electronics);
    }

    // Method to add clothing to the system
    private void addClothing(String productName, double price ,int productQty) {
        String productId = generateProductId("Clothing");
        String size = "";
        String colour = "";

        System.out.print("Enter size: ");
        size = scanner.nextLine();

        System.out.print("Enter colour: ");
        colour = scanner.nextLine();

        Clothing clothing = new Clothing(productId, productName, productQty, price, size, colour);
        addProduct(clothing);
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
    static void loadUsersFromFile() {
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

}
