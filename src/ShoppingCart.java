import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {
    private Map<Product, Integer> products;


    public  ShoppingCart() {
        this.products = new HashMap<>();
    }

    public Map<Product, Integer> getProducts() {
        return this.products;
    }

    // method to add product to cart
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

    // method to remove product from cart
    public void removeFromCart(Product product) {
        if (this.products.containsKey(product)) {
            this.products.remove(product);
            System.out.println(product.getProductName() + " removed from the cart.");

        } else {
            System.out.println("Product not found in the cart.");
        }
    }

    // method to calculate total cost of cart
    public double calculateTotalCost() {
        double totalCost = 0.0;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            totalCost += entry.getKey().getPrice() * entry.getValue();
        }
        return totalCost;
    }

    // method to calculate first purchase discount

    public double firstPurchaseDiscount(User user) {
        double totalCost = calculateTotalCost();
        if (user.isFirstPurchase()) {
            return (totalCost * 0.1);
        } else {
            return 0;
        }
    }

    // method to calculate category discount
    public double categoryDiscount() {
        int clothingCount = 0;
        int electronicsCount = 0;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            Product product = entry.getKey();
            if (product instanceof Clothing) {
                clothingCount += entry.getValue();
            } else if (product instanceof Electronics) {
                electronicsCount += entry.getValue();
            }
        }

        if (clothingCount >= 3 || electronicsCount >= 3) {
            return (calculateTotalCost() * 0.2);
        } else {
            return 0.0;
        }
    }

}
