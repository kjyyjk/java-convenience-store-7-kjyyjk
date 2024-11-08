package store;

import static store.Parser.parsePromotions;
import static store.Parser.parsePurchaseItems;
import static store.StoreFileReader.readProducts;
import static store.Parser.parseProducts;
import static store.StoreFileReader.readPromotions;
import static store.view.InputView.readPurchaseItems;
import static store.view.OutputView.printProducts;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StoreManager {
    public void run() {
        Promotions promotions = getPromotions();
        Map<String, Product> products = getProducts(promotions);
        printProducts(products);
        List<PurchaseItem> purchaseItems = parsePurchaseItems(readPurchaseItems());
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
}
