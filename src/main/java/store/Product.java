package store;

import store.Promotion;

public class Product {
    private String name;
    private int price;
    private int generalQuantity;
    private int promotionQuantity;
    private Promotion promotion;

    public Product(final String name, final int price) {
        this.name = name;
        this.price = price;
        this.generalQuantity = 0;
        this.promotionQuantity = 0;
    }

    public void doPromotion(final Promotion promotion) {
        this.promotion = promotion;
    }

    public void increaseGeneralQuantity(final int quantity) {
        generalQuantity += quantity;
    }

    public void increasePromotionQuantity(final int quantity) {
        promotionQuantity += quantity;
    }

    public int getGeneralQuantity() {
        return generalQuantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getPromotionName() {
        return promotion.getName();
    }

    public boolean hasPromotion() {
        return this.promotion != null;
    }
}
