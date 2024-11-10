package store;

import static store.FileInputParser.parseGeneralProducts;
import static store.FileInputParser.parsePromotionProducts;
import static store.FileInputParser.parsePromotions;
import static store.StoreFileReader.readProducts;
import static store.StoreFileReader.readPromotions;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import store.product.GeneralProduct;
import store.product.Products;
import store.product.PromotionProduct;

public class Application {
    public static void main(String[] args) {
        Map<String, Promotion> promotions = getPromotions();
        Map<String, PromotionProduct> promotionProducts = getPromotionProducts(promotions);
        Map<String, GeneralProduct> generalProducts = getGeneralProducts(promotionProducts);
        Products products = new Products(generalProducts, promotionProducts);
        new StoreManager().run(products);
    }

    private static Map<String, Promotion> getPromotions() {
        try {
            return parsePromotions(readPromotions());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, GeneralProduct> getGeneralProducts(
            final Map<String, PromotionProduct> promotionProducts) {
        try {
            return addBasicGeneralProducts(parseGeneralProducts(readProducts()), promotionProducts);
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

    private static Map<String, GeneralProduct> addBasicGeneralProducts(
            final Map<String, GeneralProduct> generalProducts,
            final Map<String, PromotionProduct> promotionProducts) {
        promotionProducts.values()
                .stream()
                .filter(product -> isNotContains(generalProducts, product.getName()))
                .forEach(product -> generalProducts.put(product.getName(), getBasicGeneralProduct(product)));
        return Collections.unmodifiableMap(generalProducts);
    }

    private static boolean isNotContains(final Map<String, GeneralProduct> generalProducts, final String productName) {
        return !generalProducts.containsKey(productName);
    }

    private static GeneralProduct getBasicGeneralProduct(PromotionProduct product) {
        return new GeneralProduct(product.getName(), product.getPrice(), 0);
    }
}