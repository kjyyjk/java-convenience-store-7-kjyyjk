package store;

import static store.Parser.parseProducts;
import static store.Parser.parsePromotions;
import static store.StoreFileReader.readProducts;
import static store.StoreFileReader.readPromotions;

import java.io.IOException;
import java.util.Map;

public class Application {
    public static void main(String[] args) {

        Map<String, Product> products = getProductsWithPromotion();
        new StoreManager().run(products);
    }

    private static Map<String, Product> getProductsWithPromotion() {
        try {
            Map<String, Promotion> promotions = parsePromotions(readPromotions());
            return parseProducts(readProducts(), promotions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
