package store;

public class PurchaseHistoryDetail {
    String productName;
    int productPrice;
    int totalQuantity;
    int bonusQuantity;

    public PurchaseHistoryDetail(String productName, int productPrice, int totalQuantity, int bonusQuantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.bonusQuantity = bonusQuantity;
    }
}
