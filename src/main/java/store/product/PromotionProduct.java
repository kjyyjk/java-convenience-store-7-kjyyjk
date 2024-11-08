package store.product;

import store.Promotion;

public class PromotionProduct extends Product {
    private Promotion promotion;

    public PromotionProduct(final String name, final int price, final int quantity, final Promotion promotion) {
        super(name, price, quantity);
        this.promotion = promotion;
    }
}
