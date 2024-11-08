package store;

import static store.Parser.parsePromotions;
import static store.StoreFileReader.readProducts;
import static store.Parser.parseProducts;
import static store.StoreFileReader.readPromotions;

import java.io.IOException;
import java.util.List;
import store.product.Product;

public class StoreManager {
    public void run() {
        Promotions promotions = getPromotions();
        List<Product> products = getProducts(promotions);
    }

    private static Promotions getPromotions() {
        try {
            return new Promotions(parsePromotions(readPromotions()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Product> getProducts(final Promotions promotions) {
        try {
            return parseProducts(readProducts(), promotions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
