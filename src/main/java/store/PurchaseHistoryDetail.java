package store;

public class PurchaseHistoryDetail {
    private final String productName;
    private final int productPrice;
    private final int totalQuantity;
    private final int promotionAppliedQuantity;
    private final int bonusQuantity;

    public PurchaseHistoryDetail(final String productName, final int productPrice, final int totalQuantity,
                                 final int promotionAppliedQuantity, final int bonusQuantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.promotionAppliedQuantity = promotionAppliedQuantity;
        this.bonusQuantity = bonusQuantity;
    }

    public int calculatePurchaseAmount() {
        return productPrice * totalQuantity;
    }
}
