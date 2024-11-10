package store;

import static store.FileInputParser.parseGeneralProducts;
import static store.FileInputParser.parsePromotionProducts;
import static store.FileInputParser.parsePromotions;
import static store.StoreFileReader.readProducts;
import static store.StoreFileReader.readPromotions;

import java.io.IOException;
import java.util.Map;
import store.product.GeneralProduct;
import store.product.Products;
import store.product.PromotionProduct;

public class Application {
    public static void main(String[] args) {
        Map<String, Promotion> promotions = getPromotions();
        Products products = new Products(getGeneralProducts(), getPromotionProducts(promotions));
        new StoreManager().run(products);
    }

    private static Map<String, Promotion> getPromotions() {
        try {
            return parsePromotions(readPromotions());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, GeneralProduct> getGeneralProducts() {
        try {
            return parseGeneralProducts(readProducts());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, PromotionProduct> getPromotionProducts(final Map<String, Promotion> promotions) {
        try {
            return parsePromotionProducts(readProducts(), promotions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}