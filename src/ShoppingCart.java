import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {
    private Map<Product, Integer> products;

    public ShoppingCart() {
        this.products = new HashMap<>();
    }

    public void addToCart(Product product, int quantity) {
        if (this.products.containsKey(product)) {
            quantity += this.products.get(product);
        } else {
            if (product.getProductQty() < quantity) {
                System.out.println("Not enough stock.");
                return;
            }
        }
        this.products.put(product, quantity);
        System.out.println(product.getProductName() + " added to the cart.");
    }

    public void removeFromCart(Product product) {
        if (this.products.containsKey(product)) {
            this.products.remove(product);
            System.out.println(product.getProductName() + " removed from the cart.");
        } else {
            System.out.println("Product not found in the cart.");
        }
    }

    public double calculateTotalCost() {
        double totalCost = 0.0;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            totalCost += entry.getKey().getPrice() * entry.getValue();
        }
        return totalCost;
    }

    public double firstPurchaseDiscount(User user) {
        double totalCost = calculateTotalCost();
        if (user.isFirstPurchase()) {
            user.setFirstPurchase(false);
            return (totalCost * 0.1);
        } else {
            return 0;
        }
    }

    public double categoryDiscount() {
        int clothingCount = 0;
        int electronicsCount = 0;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            Product product = entry.getKey(); // Use getClass().getName() to get the category
            if (product instanceof Clothing) {
                if (entry.getValue() >= 3) {
                    return (calculateTotalCost() * 0.2);
                } else {
                    clothingCount++;
                }
            } else if (product instanceof Electronics) {
                if (entry.getValue() >= 3) {
                    return (calculateTotalCost() * 0.2);
                } else {
                    electronicsCount++;
                }
            }
        }

        if (clothingCount >= 3) {
            return (calculateTotalCost() * 0.2);
        } else if (electronicsCount >= 3) {
            return (calculateTotalCost() * 0.2);
        } else {
            return 0;
        }

    }

    public Map<Product, Integer> getProducts() {
        return this.products;
    }

}