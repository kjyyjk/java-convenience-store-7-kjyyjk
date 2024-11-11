package store.product;

import java.time.LocalDate;
import store.Promotion;

public class PromotionProduct extends Product {
    private final Promotion promotion;

    public PromotionProduct(final String name, final int price, final int quantity, final Promotion promotion) {
        super(name, price, quantity);
        this.promotion = promotion;
    }

    public int getExtraBonusQuantity(final int purchaseQuantity) {
        int extraBonusQuantity = promotion.getExtraBonusQuantity(purchaseQuantity);
        if (extraBonusQuantity + purchaseQuantity > getQuantity()) {
            return 0;
        }
        return extraBonusQuantity;
    }

    public int getNotPromotionAppliedQuantity(final int purchaseQuantity) {
        if (purchaseQuantity <= getQuantity()) {
            return promotion.getNotPromotionAppliedQuantity(getQuantity(), purchaseQuantity);
        }
        return promotion.getNotPromotionAppliedQuantity(getQuantity(), getQuantity());
    }

    public boolean isDoingPromotion(final LocalDate todayDate) {
        return promotion.isDoing(todayDate);
    }

    public int getPromotionAppliedQuantity(final int purchaseQuantity) {
        if (purchaseQuantity <= getQuantity()) {
            return promotion.getAppliedQuantity(purchaseQuantity);
        }
        return promotion.getAppliedQuantity(getQuantity());
    }

    public int getTotalBonusQuantity(final int purchaseQuantity) {
        if (purchaseQuantity <= getQuantity()) {
            return promotion.getTotalBonusQuantity(purchaseQuantity);
        }
        return promotion.getTotalBonusQuantity(getQuantity());
    }

    public int decreaseQuantity(final int quantity) {
        return super.decreaseQuantity(quantity);
    }

    public String getPromotionName() {
        return promotion.getName();
    }
}