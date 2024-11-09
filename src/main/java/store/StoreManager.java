package store;

import static store.Parser.parseExtraBonusQuantity;
import static store.Parser.parseNoPromotionQuantity;
import static store.Parser.parsePromotions;
import static store.Parser.parsePurchaseItems;
import static store.StoreFileReader.readProducts;
import static store.Parser.parseProducts;
import static store.StoreFileReader.readPromotions;
import static store.view.InputView.readExtraBonusQuantity;
import static store.view.InputView.readNoPromotionQuantity;
import static store.view.InputView.readPurchaseItems;
import static store.view.OutputView.printProducts;

import camp.nextstep.edu.missionutils.DateTimes;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.view.OutputView;

public class StoreManager {
    public void run() {
        Promotions promotions = getPromotions();
        Map<String, Product> products = getProducts(promotions);
        printProducts(products);
        purchase(products);
    }

    private List<PurchaseHistoryDetail> purchase(Map<String, Product> products) {
        try {
            List<PurchaseItem> purchaseItems = parsePurchaseItems(readPurchaseItems());
            List<PurchaseHistoryDetail> purchaseHistory = new ArrayList<>();
            for (PurchaseItem purchaseItem : purchaseItems) {
                int totalBonusQuantity = 0;
                int purchaseQuantity = purchaseItem.getQuantity();
                Product product = products.get(purchaseItem.getName());
                String productName = product.getName();
                product.validateExceedQuantity(purchaseQuantity);
                boolean doingPromotion = product.isDoingPromotion(getTodayLocalDate());
                if (doingPromotion) {
                    int extraBonusQuantity = product.getExtraBonusQuantity(purchaseQuantity);
                    if (extraBonusQuantity > 0) {
                        if (parseExtraBonusQuantity(readExtraBonusQuantity(productName, extraBonusQuantity))) {
                            purchaseQuantity += extraBonusQuantity;
                        }
                    } else {
                        int noPromotionQuantity = product.getNoPromotionQuantity(purchaseQuantity);
                        if (noPromotionQuantity > 0) {
                            if (!parseNoPromotionQuantity(
                                    readNoPromotionQuantity(productName, noPromotionQuantity))) {
                                purchaseQuantity -= noPromotionQuantity;
                            }
                        }
                    }

                    totalBonusQuantity = product.getTotalBonusQuantity(purchaseQuantity);
                }
                product.decreaseQuantity(purchaseQuantity);
                PurchaseHistoryDetail purchaseHistoryDetail = new PurchaseHistoryDetail(productName, product.getPrice(),
                        purchaseQuantity, totalBonusQuantity);
                purchaseHistory.add(purchaseHistoryDetail);
            }

            return purchaseHistory;
        } catch (IllegalArgumentException e) {
            OutputView.printError(e);
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
}
