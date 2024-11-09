package store;

import java.util.List;

public class PurchaseHistory {
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    private static final int MEMBERSHIP_DISCOUNT_LIMIT = 8000;
    private final List<PurchaseHistoryDetail> purchaseHistory;
    private final boolean membershipDiscount;

    public PurchaseHistory(final List<PurchaseHistoryDetail> purchaseHistory, final boolean membershipDiscount) {
        this.purchaseHistory = purchaseHistory;
        this.membershipDiscount = membershipDiscount;
    }

    public int calculateTotalPurchaseAmount() {
        int totalPurchaseAmount = 0;
        for (PurchaseHistoryDetail detail : purchaseHistory) {
            totalPurchaseAmount += detail.calculatePurchaseAmount();
        }
        return totalPurchaseAmount;
    }

    public int calculatePromotionDiscountAmount() {
        int promotionDiscountAmount = 0;
        for (PurchaseHistoryDetail detail : purchaseHistory) {
            promotionDiscountAmount += detail.calculatePromotionDiscountAmount();
        }
        return promotionDiscountAmount;
    }

    public int calculateMembershipDiscountAmount() {
        int amount = calculateTotalPurchaseAmount() - calculatePromotionAppliedAmount();
        int membershipDiscountAmount = (int) (amount * MEMBERSHIP_DISCOUNT_RATE);
        if (membershipDiscountAmount > MEMBERSHIP_DISCOUNT_LIMIT) {
            return MEMBERSHIP_DISCOUNT_LIMIT;
        }
        return membershipDiscountAmount;
    }

    public int calculatePayAmount() {
        int payAmount = calculateTotalPurchaseAmount() - calculatePromotionDiscountAmount();
        if (membershipDiscount) {
            return payAmount - calculateMembershipDiscountAmount();
        }
        return payAmount;
    }

    private int calculatePromotionAppliedAmount() {
        int promotionAppliedAmount = 0;
        for (PurchaseHistoryDetail detail : purchaseHistory) {
            promotionAppliedAmount += detail.calculatePromotionAppliedAmount();
        }
        return promotionAppliedAmount;
    }
}
