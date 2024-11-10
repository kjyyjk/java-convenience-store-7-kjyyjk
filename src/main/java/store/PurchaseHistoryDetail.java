package store;

public class PurchaseHistoryDetail {
    private final String productName;
    private final int productPrice;
    private final int totalQuantity;
    private final int promotionAppliedQuantity;
    private final int bonusQuantity;

    public PurchaseHistoryDetail(final Product product, final int totalQuantity,
                                 final int promotionAppliedQuantity, final int bonusQuantity) {
        this.productName = product.getName();
        this.productPrice = product.getPrice();
        this.totalQuantity = totalQuantity;
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
        return totalQuantity;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public boolean hasBonusQuantity() {
        return bonusQuantity > 0;
    }

    public int calculatePurchaseAmount() {
        return productPrice * totalQuantity;
    }

    public int calculatePromotionDiscountAmount() {
        return productPrice * bonusQuantity;
    }

    public int calculatePromotionAppliedAmount() {
        return productPrice * promotionAppliedQuantity;
    }
}
