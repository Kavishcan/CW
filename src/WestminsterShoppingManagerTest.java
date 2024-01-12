import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {

    private WestminsterShoppingManager westminsterShoppingManager;

    @BeforeEach
    void setUp() {
        // Initialize the WestminsterShoppingManager instance
        westminsterShoppingManager = new WestminsterShoppingManager();
    }

    @Test
    void testAddProduct() {
        // Create a new product
        Product product = new Electronics("E001", "Test Product", 10, 100.0, "Test Brand", 12);

        // Add the product to the WestminsterShoppingManager
        westminsterShoppingManager.addProduct(product);

        // Assert that the product is in the productList of the WestminsterShoppingManager
        assertTrue(WestminsterShoppingManager.getProductList().contains(product));
    }

    @Test
    void testRemoveProduct() {
        // Create a new product
        Product product = new Electronics("E001", "Test Product", 10, 100.0, "Test Brand", 12);

        // Add the product to the WestminsterShoppingManager
        westminsterShoppingManager.addProduct(product);

        String productId = product.getProductId();
        // Remove the product from the WestminsterShoppingManager
        westminsterShoppingManager.removeProduct(productId);

        // Assert that the product is no longer in the productList of the WestminsterShoppingManager
        assertFalse(WestminsterShoppingManager.getProductList().contains(product));
    }

    @Test
    void testDisplayProducts() {
        // Create a new product
        Product product1 = new Electronics("E001", "Test Product 1", 10, 100.0, "Test Brand", 12);
        Product product2 = new Electronics("E002", "Test Product 2", 20, 200.0, "Test Brand", 24);

        // Add the products to the WestminsterShoppingManager
        westminsterShoppingManager.addProduct(product1);
        westminsterShoppingManager.addProduct(product2);

        // Capture the console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the displayProducts method
        westminsterShoppingManager.displayProducts();

        // Check if the output is as expected
        String expectedOutput = "Products in the system:\r\n" +
                "----------------------------------------\r\n" +
                "Product Name: Test Product 1\r\n" +
                "Product ID: E001\r\n" +
                "Price: $100.0\r\n" +
                "Quantity: 10\r\n" +
                "Type: Electronics\r\n" +
                "Brand: Test Brand\r\n" +
                "Warranty Period: 12 months\r\n" +
                "----------------------------------------\r\n" +
                "----------------------------------------\r\n" +
                "Product Name: Test Product 2\r\n" +
                "Product ID: E002\r\n" +
                "Price: $200.0\r\n" +
                "Quantity: 20\r\n" +
                "Type: Electronics\r\n" +
                "Brand: Test Brand\r\n" +
                "Warranty Period: 24 months\r\n" +
                "----------------------------------------\r\n";


        assertEquals(expectedOutput, outContent.toString());

        // Reset the console output to its original stream
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    @Test
    void testUpdateProductQty() {
        // Create a new product
        Product product = new Electronics("E001", "Test Product", 10, 100.0, "Test Brand", 12);

        // Add the product to the WestminsterShoppingManager
        westminsterShoppingManager.addProduct(product);

        // Update the quantity of the product in the WestminsterShoppingManager
        westminsterShoppingManager.updateProductQty(product.getProductId(), 20);

        // Assert that the quantity of the product in the productList of the WestminsterShoppingManager is updated correctly
        assertEquals(20, WestminsterShoppingManager.getProduct(product.getProductId()).getProductQty());
    }

    @Test
    void testSaveToFile() {
        // Create a new product
    Product product = new Electronics("E001", "Test Product", 10, 100.0, "Test Brand", 12);

        // Add the product to the WestminsterShoppingManager
        westminsterShoppingManager.addProduct(product);

        // Call the saveToFile method
        westminsterShoppingManager.saveToFile();

        // Read the file and check if the product details are saved correctly
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("products.txt"))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                ArrayList<Product> productsFromFile = (ArrayList<Product>) obj;
                for (Product productFile : productsFromFile) {
                    if (productFile.getProductId().equals(product.getProductId())) {
                        assertEquals(product, productFile);
                    }
                }
            } else {
                fail("The file does not contain a list of products");
            }
        } catch (IOException | ClassNotFoundException e) {
            fail("Failed to read the file");
        }
    }

    @Test
    void testOpenGUI() {
        // Test if the GUI is opened correctly
        assertDoesNotThrow(() -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setVisible(true);
        });
    }
}
