package store;

import static store.Parser.parseExtraBonusQuantity;
import static store.Parser.parseNoPromotionQuantity;
import static store.Parser.parsePromotions;
import static store.Parser.parsePurchaseItems;
import static store.StoreFileReader.readProducts;
import static store.Parser.parseProducts;
import static store.StoreFileReader.readPromotions;
import static store.view.InputView.readExtraPromotionQuantity;
import static store.view.InputView.readNoPromotionQuantity;
import static store.view.InputView.readPurchaseItems;
import static store.view.OutputView.printProducts;

import camp.nextstep.edu.missionutils.DateTimes;
import java.io.IOException;
import java.time.LocalDate;
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

    private void purchase(Map<String, Product> products) {
        try {
            List<PurchaseItem> purchaseItems = parsePurchaseItems(readPurchaseItems());
            for (PurchaseItem purchaseItem : purchaseItems) {
                Product product = products.get(purchaseItem.getName());
                product.validateExceedQuantity(purchaseItem.getQuantity());
                boolean doingPromotion = product.isDoingPromotion(getTodayLocalDate());
                if (doingPromotion) {
                    int extraPromotionQuantity = product.getExtraPromotionQuantity(purchaseItem.getQuantity());
                    if (extraPromotionQuantity > 0) {
                        if (parseExtraBonusQuantity(readExtraPromotionQuantity(product.getName(), extraPromotionQuantity))) {
                            purchaseItem.increaseQuantity(extraPromotionQuantity);
                        }
                    } else {
                        int noPromotionQuantity = product.getNoPromotionQuantity(purchaseItem.getQuantity());
                        if (noPromotionQuantity > 0) {
                            if (!parseNoPromotionQuantity(
                                    readNoPromotionQuantity(product.getName(), noPromotionQuantity))) {
                                purchaseItem.decreaseQuantity(noPromotionQuantity);
                            }
                        }
                    }

                }
            }
        } catch (IllegalArgumentException e) {
            OutputView.printError(e);
            purchase(products);
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
