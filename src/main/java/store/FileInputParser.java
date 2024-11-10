package store;

import static store.InputParser.parseInt;
import static store.InputParser.parseLocalDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FileInputParser {
    private static final String PRODUCT_INFORMATION_DELIMITER = ",";
    private static final String PROMOTION_INFORMATION_DELIMITER = ",";
    public static Map<String, Promotion> parsePromotions(final BufferedReader bufferedReader) throws IOException {
        Map<String, Promotion> promotions = new HashMap<>();
        String promotionInformation = bufferedReader.readLine();
        while ((promotionInformation = bufferedReader.readLine()) != null) {
            Promotion promotion = parsePromotion(promotionInformation);
            promotions.put(promotion.getName(), promotion);
        }
        return Collections.unmodifiableMap(promotions);
    }

    private static Promotion parsePromotion(final String promotionInformation) {
        String[] split = promotionInformation.split(PROMOTION_INFORMATION_DELIMITER);
        String name = split[0];
        int buyQuantity = parseInt(split[1]);
        int bonusQuantity = parseInt(split[2]);
        LocalDate startDate = parseLocalDate(split[3]);
        LocalDate endDate = parseLocalDate(split[4]);
        return new Promotion(name, buyQuantity, bonusQuantity, startDate, endDate);
    }

    public static Map<String, Product> parseProducts(final BufferedReader bufferedReader, final Map<String, Promotion> promotions)
            throws IOException {
        Map<String, Product> products = new HashMap<>();
        String productInformation = bufferedReader.readLine();
        while ((productInformation = bufferedReader.readLine()) != null) {
            String[] split = productInformation.split(PRODUCT_INFORMATION_DELIMITER);
            Product product = getProduct(promotions, products, split);
            products.put(product.getName(), product);
        }
        return Collections.unmodifiableMap(products);
    }

    private static Product getProduct(Map<String, Promotion> promotions, Map<String, Product> products, String[] split) {
        String name = split[0];
        int price = parseInt(split[1]);
        int quantity = parseInt(split[2]);
        Promotion promotion = promotions.getOrDefault(split[3], null);
        Product product = getProduct(products, name, price);
        increaseProductQuantity(product, promotion, quantity);
        return product;
    }

    private static Product getProduct(Map<String, Product> products, String name, int price) {
        if (products.containsKey(name)) {
            return products.get(name);
        }
        return new Product(name, price);
    }

    private static void increaseProductQuantity(Product product, Promotion promotion, int quantity) {
        if (promotion == null) {
            product.increaseGeneralQuantity(quantity);
            return;
        }

        product.doPromotion(promotion);
        product.increasePromotionQuantity(quantity);
    }
}
