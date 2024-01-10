public interface ShoppingManager {
    void addProduct(Product product);

    void removeProduct();

    void displayProducts();

    void updateProductQty();

    void saveToFile();

    void loadFromFile();

    void openGUI();

}
