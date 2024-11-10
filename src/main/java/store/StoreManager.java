package store;

import static store.Parser.parsePurchaseItems;
import static store.Parser.parseYesOrNoToBoolean;
import static store.view.InputView.readAdditionalPurchase;
import static store.view.InputView.readPurchaseExtraPromotionQuantity;
import static store.view.InputView.readGetMembershipDiscount;
import static store.view.InputView.readPurchasePromotionNotAppliedQuantity;
import static store.view.InputView.readPurchaseItems;
import static store.view.OutputView.printError;
import static store.view.OutputView.printProducts;
import static store.view.OutputView.printReceipt;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoreManager {

    private static final String NOT_EXIST_PRODUCT_ERROR_MESSAGE = "존재하지 않는 상품입니다. 다시 입력해 주세요.";

    public void run(final Map<String, Product> products) {
        printProducts(products);
        PurchaseHistory purchaseHistory = purchase(products);
        printReceipt(purchaseHistory);
        if (isWillAdditionalPurchase()) {
            run(products);
        }
    }

    private PurchaseHistory purchase(Map<String, Product> products) {
        try {
            List<PurchaseItem> purchaseItems = parsePurchaseItems(readPurchaseItems());
            List<PurchaseHistoryDetail> purchaseHistory = purchaseProducts(products, purchaseItems);
            return new PurchaseHistory(purchaseHistory, isWillGetMembershipDiscount());
        } catch (IllegalArgumentException e) {
            printError(e);
            return purchase(products);
        }
    }

    private List<PurchaseHistoryDetail> purchaseProducts(Map<String, Product> products,
                                                         List<PurchaseItem> purchaseItems) {
        List<PurchaseHistoryDetail> purchaseHistory = new ArrayList<>();
        for (PurchaseItem purchaseItem : purchaseItems) {
            int purchaseQuantity = purchaseItem.getQuantity();
            Product product = getProductByName(products, purchaseItem.getName());
            PurchaseHistoryDetail purchaseHistoryDetail = updateProductQuantity(purchaseQuantity, product);
            purchaseHistory.add(purchaseHistoryDetail);
        }
        return purchaseHistory;
    }

    private PurchaseHistoryDetail updateProductQuantity(int purchaseQuantity, Product product) {
        int totalBonusQuantity = 0;
        int promotionAppliedQuantity = 0;
        product.validateExceedQuantity(purchaseQuantity);

        int finalPurchaseQuantity = purchaseQuantity;
        if (product.isDoingPromotion(getTodayLocalDate())) {
            finalPurchaseQuantity = getFinalPurchaseQuantity(purchaseQuantity, product);
            promotionAppliedQuantity = product.getPromotionAppliedQuantity(finalPurchaseQuantity);
            totalBonusQuantity = product.getTotalBonusQuantity(finalPurchaseQuantity);
        }
        product.decreaseQuantity(finalPurchaseQuantity);
        return new PurchaseHistoryDetail(product, finalPurchaseQuantity, promotionAppliedQuantity, totalBonusQuantity);
    }

    private int getFinalPurchaseQuantity(int purchaseQuantity, Product product) {
        int extraBonusQuantity = product.getExtraBonusQuantity(purchaseQuantity);
        if (extraBonusQuantity > 0) {
            return purchaseQuantity + getExtraQuantity(product, extraBonusQuantity);
        }
        int promotionNotAppliedQuantity = product.getNoPromotionQuantity(purchaseQuantity);
        if (promotionNotAppliedQuantity > 0) {
            return purchaseQuantity - getExcludeQuantity(product, promotionNotAppliedQuantity);
        }
        return purchaseQuantity;
    }

    private int getExtraQuantity(Product product, int extraBonusQuantity) {
        if (isWillGetExtraBonusQuantity(product.getName(), extraBonusQuantity)) {
            return extraBonusQuantity;
        }
        return 0;
    }

    private int getExcludeQuantity(Product product, int promotionNotAppliedQuantity) {
        if (isWillPurchasePromotionNotAppliedQuantity(product.getName(), promotionNotAppliedQuantity)) {
            return 0;
        }
        return promotionNotAppliedQuantity;
    }

    private Product getProductByName(Map<String, Product> products, String name) {
        Product product = products.getOrDefault(name, null);
        if (product == null) {
            throw new IllegalArgumentException(NOT_EXIST_PRODUCT_ERROR_MESSAGE);
        }
        return product;
    }

    private LocalDate getTodayLocalDate() {
        return DateTimes.now().toLocalDate();
    }

    private boolean isWillGetMembershipDiscount() {
        try {
            return parseYesOrNoToBoolean(readGetMembershipDiscount());
        } catch (IllegalArgumentException e) {
            printError(e);
            return isWillGetMembershipDiscount();
        }
    }

    private boolean isWillPurchasePromotionNotAppliedQuantity(final String productName,
                                                              final int promotionNotAppliedQuantity) {
        try {
            return parseYesOrNoToBoolean(
                    readPurchasePromotionNotAppliedQuantity(productName, promotionNotAppliedQuantity));
        } catch (IllegalArgumentException e) {
            printError(e);
            return isWillPurchasePromotionNotAppliedQuantity(productName, promotionNotAppliedQuantity);
        }
    }

    private boolean isWillGetExtraBonusQuantity(final String productName, final int extraBonusQuantity) {
        try {
            return parseYesOrNoToBoolean(readPurchaseExtraPromotionQuantity(productName, extraBonusQuantity));
        } catch (IllegalArgumentException e) {
            printError(e);
            return isWillGetExtraBonusQuantity(productName, extraBonusQuantity);
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
