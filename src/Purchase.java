public class Purchase {
    private Integer purchaseId;
    private ShoppingCart shoppingCart;

    public Purchase(Integer purchaseId, ShoppingCart shoppingCart) {
        this.purchaseId = purchaseId;
        this.shoppingCart = shoppingCart;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

}
