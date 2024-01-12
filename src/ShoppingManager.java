public interface ShoppingManager {
    void addProduct(Product product);

    void removeProduct(String productId);

    void displayProducts();

    void updateProductQty(String productIdToUpdate, int newQty);

    void    saveToFile();

    void loadFromFile();

    void openGUI();

}
