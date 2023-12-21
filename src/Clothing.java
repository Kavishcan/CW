public class Clothing extends Product {
    private String size;
    private String colour;

    public Clothing(String productId, String productName, int productQty, double price, String size, String colour) {
        super(productId, productName, productQty, price);
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getInfo() {
        return size + " " + colour;
    }
}
