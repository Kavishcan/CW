public class Electronics extends Product {

    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId, String productName, int productQty, double price, String brand,
            int warrantyPeriod) {
        super(productId, productName, productQty, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getInfo() {
        return brand + " " + warrantyPeriod + " months";
    }

}
