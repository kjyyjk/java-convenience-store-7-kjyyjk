package store;

import static store.Parser.parsePromotions;
import static store.Parser.parsePurchaseItems;
import static store.Parser.parseYesOrNoToBoolean;
import static store.StoreFileReader.readProducts;
import static store.Parser.parseProducts;
import static store.StoreFileReader.readPromotions;
import static store.StoreFileWriter.updateProducts;
import static store.view.InputView.readAdditionalPurchase;
import static store.view.InputView.readPurchaseExtraPromotionQuantity;
import static store.view.InputView.readGetMembershipDiscount;
import static store.view.InputView.readPurchasePromotionNotAppliedQuantity;
import static store.view.InputView.readPurchaseItems;
import static store.view.OutputView.printError;
import static store.view.OutputView.printProducts;
import static store.view.OutputView.printReceipt;

import camp.nextstep.edu.missionutils.DateTimes;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoreManager {
    public void run() {
        Promotions promotions = getPromotions();
        Map<String, Product> products = getProducts(promotions);
        printProducts(products);
        PurchaseHistory purchaseHistory = purchase(products);
        printReceipt(purchaseHistory);
        try {
            updateProducts(products);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (isWillAdditionalPurchase()) {
            run();
        }
    }

    private PurchaseHistory purchase(Map<String, Product> products) {
        try {
            List<PurchaseItem> purchaseItems = parsePurchaseItems(readPurchaseItems());
            List<PurchaseHistoryDetail> purchaseHistory = new ArrayList<>();
            for (PurchaseItem purchaseItem : purchaseItems) {
                int totalBonusQuantity = 0;
                int promotionAppliedQuantity = 0;
                int purchaseQuantity = purchaseItem.getQuantity();
                String productName = purchaseItem.getName();
                Product product = products.get(purchaseItem.getName());
                product.validateExceedQuantity(purchaseQuantity);
                boolean doingPromotion = product.isDoingPromotion(getTodayLocalDate());
                if (doingPromotion) {
                    int extraBonusQuantity = product.getExtraBonusQuantity(purchaseQuantity);
                    if (extraBonusQuantity > 0) {
                        if (isWillGetExtraBonusQuantity(productName, extraBonusQuantity)) {
                            purchaseQuantity += extraBonusQuantity;
                        }
                    } else {
                        int promotionNotAppliedQuantity = product.getNoPromotionQuantity(purchaseQuantity);
                        if (promotionNotAppliedQuantity > 0) {
                            if (!isWillPurchasePromotionNotAppliedQuantity(productName, promotionNotAppliedQuantity)) {
                                purchaseQuantity -= promotionNotAppliedQuantity;
                            }
                        }
                    }

                    promotionAppliedQuantity = product.getPromotionAppliedQuantity(purchaseQuantity);
                    totalBonusQuantity = product.getTotalBonusQuantity(purchaseQuantity);
                }
                product.decreaseQuantity(purchaseQuantity);
                PurchaseHistoryDetail purchaseHistoryDetail = new PurchaseHistoryDetail(productName, product.getPrice(),
                        purchaseQuantity, promotionAppliedQuantity, totalBonusQuantity);
                purchaseHistory.add(purchaseHistoryDetail);
            }
            return new PurchaseHistory(purchaseHistory, isWillGetMembershipDiscount());
        } catch (IllegalArgumentException e) {
            printError(e);
            return purchase(products);
        }
    }
    
    private static Promotions getPromotions() {
        try {
            return new Promotions(parsePromotions(readPromotions()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Product> getProducts(final Promotions promotions) {
        try {
            return parseProducts(readProducts(), promotions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private boolean isWillPurchasePromotionNotAppliedQuantity(final String productName, final int promotionNotAppliedQuantity) {
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
