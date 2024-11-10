package store;

import java.time.LocalDate;

public class Product {
    private static final String EXCEED_QUANTITY_ERROR = "재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
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

    public void validateExceedQuantity(final int purchaseQuantity) {
        if (generalQuantity + promotionQuantity < purchaseQuantity) {
            throw new IllegalArgumentException(EXCEED_QUANTITY_ERROR);
        }
    }

    public boolean isDoingPromotion(final LocalDate date) {
        if (hasPromotion()) {
            return promotion.isDoing(date);
        }
        return false;
    }

    public int getExtraBonusQuantity(final int purchaseQuantity) {
        int extraBonusQuantity = promotion.getExtraBonusQuantity(purchaseQuantity);
        if (extraBonusQuantity + purchaseQuantity <= promotionQuantity) {
            return extraBonusQuantity;
        }
        return 0;
    }

    public int getNoPromotionQuantity(final int purchaseQuantity) {
        return promotion.getNoPromotionQuantity(promotionQuantity, purchaseQuantity);
    }

    public void decreaseQuantity(final int purchaseQuantity) {
        validateExceedQuantity(purchaseQuantity);
        if (promotionQuantity > purchaseQuantity) {
            promotionQuantity -= purchaseQuantity;
            return;
        }

        generalQuantity -= (purchaseQuantity - promotionQuantity);
        promotionQuantity = 0;
    }

    public int getTotalBonusQuantity(final int purchaseQuantity) {
        if (purchaseQuantity <= promotionQuantity) {
            return promotion.getTotalBonusQuantity(purchaseQuantity);
        }
        return promotion.getTotalBonusQuantity(promotionQuantity);
    }

    public int getPromotionAppliedQuantity(int purchaseQuantity) {
        if (purchaseQuantity <= promotionQuantity) {
            return promotion.getAppliedQuantity(purchaseQuantity);
        }
        return promotion.getAppliedQuantity(promotionQuantity);
    }
}
