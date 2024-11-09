package store;

import java.time.LocalDate;

public class Product {
    private String name;
    private int price;
    private int generalQuantity;
    private int promotionQuantity;
    private Promotion promotion;
    private static final String EXCEED_QUANTITY_ERROR = "재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";

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

    public void validateExceedQuantity(final int purchaseQuantity) {
        if (generalQuantity + promotionQuantity < purchaseQuantity) {
            throw new IllegalArgumentException(EXCEED_QUANTITY_ERROR);
        }
    }

    public boolean isDoingPromotion(final LocalDate date) {
        return promotion.isDoing(date);
    }

    public int getExtraPromotionQuantity(final int purchaseQuantity) {
        int extraQuantity = promotion.getExtraQuantity(purchaseQuantity);
        if (extraQuantity + purchaseQuantity <= promotionQuantity) {
            return extraQuantity;
        }
        return 0;
    }
}
