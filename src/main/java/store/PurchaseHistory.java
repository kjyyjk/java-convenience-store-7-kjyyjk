package store;

import java.util.List;

public class PurchaseHistory {
    private final List<PurchaseHistoryDetail> purchaseHistory;
    private final boolean membershipDiscount;

    public PurchaseHistory(final List<PurchaseHistoryDetail> purchaseHistory, final boolean membershipDiscount) {
        this.purchaseHistory = purchaseHistory;
        this.membershipDiscount = membershipDiscount;
    }
}
