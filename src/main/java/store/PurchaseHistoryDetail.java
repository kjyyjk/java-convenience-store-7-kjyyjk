package store;

public class PurchaseHistoryDetail {
    private final String productName;
    private final int productPrice;
    private final int purchaseQuantity;
    private final int promotionAppliedQuantity;
    private final int bonusQuantity;

    public PurchaseHistoryDetail(final String productName, final int productPrice, final int purchaseQuantity,
                                 final int promotionAppliedQuantity, final int bonusQuantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.purchaseQuantity = purchaseQuantity;
        this.promotionAppliedQuantity = promotionAppliedQuantity;
        this.bonusQuantity = bonusQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    public int getTotalQuantity() {
        return purchaseQuantity;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public boolean hasBonusQuantity() {
        return bonusQuantity > 0;
    }

    public int calculatePurchaseAmount() {
        return productPrice * purchaseQuantity;
    }

    public int calculatePromotionDiscountAmount() {
        return productPrice * bonusQuantity;
    }

    public int calculatePromotionAppliedAmount() {
        return productPrice * promotionAppliedQuantity;
    }

    public boolean isNotZeroQuantity() {
        return purchaseQuantity > 0;
    }
}
