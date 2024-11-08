package store;

import static store.Parser.parsePromotions;
import static store.StoreFileReader.readProducts;
import static store.Parser.parseProducts;
import static store.StoreFileReader.readPromotions;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import store.product.Product;

public class StoreManager {
    public void run() {
        List<Product> products = getProducts();
        Map<String, Promotion> promotions = getPromotions();
    }

    private static Map<String, Promotion> getPromotions() {
        try {
            return parsePromotions(readPromotions());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Product> getProducts() {
        try {
            return parseProducts(readProducts());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
