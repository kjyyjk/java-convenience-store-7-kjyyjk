package store.product;

public class PromotionProduct extends Product {
    private String promotion;

    public PromotionProduct(final String name, final int price, final int quantity, final String promotion) {
        super(name, price, quantity);
        this.promotion = promotion;
    }
}
