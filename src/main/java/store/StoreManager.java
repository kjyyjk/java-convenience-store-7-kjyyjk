package store;

import static store.InputParser.parsePurchaseItems;
import static store.InputParser.parseYesOrNoToBoolean;
import static store.view.InputView.readAdditionalPurchase;
import static store.view.InputView.readGetMembershipDiscount;
import static store.view.InputView.readPurchaseItems;
import static store.view.OutputView.printError;
import static store.view.OutputView.printProducts;
import static store.view.OutputView.printReceipt;

import java.util.ArrayList;
import java.util.List;
import store.product.Products;

public class StoreManager {
    public void run(final Products products) {
        printProducts(products);
        PurchaseHistory purchaseHistory = purchase(products);
        if (!purchaseHistory.isTotalPurchaseQuantityEqualsZero()) {
            printReceipt(purchaseHistory);
        }
        if (isWillAdditionalPurchase()) {
            run(products);
        }
    }

    private PurchaseHistory purchase(final Products products) {
        try {
            List<PurchaseItem> purchaseItems = parsePurchaseItems(readPurchaseItems());
            List<PurchaseHistoryDetail> purchaseHistory = purchaseProducts(products, purchaseItems);
            return new PurchaseHistory(purchaseHistory, isWillGetMembershipDiscount());
        } catch (IllegalArgumentException e) {
            printError(e);
            return purchase(products);
        }
    }

    private List<PurchaseHistoryDetail> purchaseProducts(final Products products,
                                                         final List<PurchaseItem> purchaseItems) {
        List<PurchaseHistoryDetail> purchaseHistory = new ArrayList<>();
        for (PurchaseItem purchaseItem : purchaseItems) {
            products.validateProductExists(purchaseItem.getName());
            products.validateProductQuantity(purchaseItem.getName(), purchaseItem.getQuantity());
            PurchaseHistoryDetail purchaseHistoryDetail = products.purchaseProduct(purchaseItem.getName(),
                    purchaseItem.getQuantity());
            purchaseHistory.add(purchaseHistoryDetail);
        }
        return purchaseHistory;
    }

    private boolean isWillGetMembershipDiscount() {
        try {
            return parseYesOrNoToBoolean(readGetMembershipDiscount());
        } catch (IllegalArgumentException e) {
            printError(e);
            return isWillGetMembershipDiscount();
        }
    }

    private boolean isWillAdditionalPurchase() {
        try {
            return parseYesOrNoToBoolean(readAdditionalPurchase());
        } catch (IllegalArgumentException e) {
            printError(e);
            return isWillAdditionalPurchase();
        }
    }
}
