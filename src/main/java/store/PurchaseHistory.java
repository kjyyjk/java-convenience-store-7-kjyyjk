package store;

import java.util.List;

public class PurchaseHistory {
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    private static final int MEMBERSHIP_DISCOUNT_LIMIT = 8000;
    private final List<PurchaseHistoryDetail> purchaseHistoryDetails;
    private final boolean membershipDiscount;

    public PurchaseHistory(final List<PurchaseHistoryDetail> purchaseHistoryDetails, final boolean membershipDiscount) {
        this.purchaseHistoryDetails = purchaseHistoryDetails;
        this.membershipDiscount = membershipDiscount;
    }

    public List<PurchaseHistoryDetail> getPurchaseHistoryDetails() {
        return purchaseHistoryDetails.stream()
                .filter(PurchaseHistoryDetail::isNotZeroQuantity)
                .toList();
    }

    public List<PurchaseHistoryDetail> getBonusPurchaseHistoryDetails() {
        return purchaseHistoryDetails.stream()
                .filter(PurchaseHistoryDetail::isNotZeroQuantity)
                .filter(PurchaseHistoryDetail::hasBonusQuantity)
                .toList();
    }

    public int calculateTotalPurchaseQuantity() {
        int totalPurchaseQuantity = 0;
        for (PurchaseHistoryDetail detail : purchaseHistoryDetails) {
            totalPurchaseQuantity += detail.getTotalQuantity();
        }
        return totalPurchaseQuantity;
    }

    public int calculateTotalPurchaseAmount() {
        int totalPurchaseAmount = 0;
        for (PurchaseHistoryDetail detail : purchaseHistoryDetails) {
            totalPurchaseAmount += detail.calculatePurchaseAmount();
        }
        return totalPurchaseAmount;
    }

    public int calculatePromotionDiscountAmount() {
        int promotionDiscountAmount = 0;
        for (PurchaseHistoryDetail detail : purchaseHistoryDetails) {
            promotionDiscountAmount += detail.calculatePromotionDiscountAmount();
        }
        return promotionDiscountAmount;
    }

    public int calculateMembershipDiscountAmount() {
        if (!membershipDiscount) {
            return 0;
        }
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

    public boolean isTotalPurchaseQuantityEqualsZero() {
        return (getPurchaseHistoryDetails().size() == 0);
    }

    private int calculatePromotionAppliedAmount() {
        int promotionAppliedAmount = 0;
        for (PurchaseHistoryDetail detail : purchaseHistoryDetails) {
            promotionAppliedAmount += detail.calculatePromotionAppliedAmount();
        }
        return promotionAppliedAmount;
    }
}
