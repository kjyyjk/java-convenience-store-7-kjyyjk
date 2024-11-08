package store;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import store.product.GeneralProduct;
import store.product.Product;
import store.product.PromotionProduct;

public class Parser {
    private static final String PRODUCT_INFORMATION_DELIMITER = ",";
    private static final String PROMOTION_INFORMATION_DELIMITER = ",";
    private static final String NO_PROMOTION = "null";

    public static List<Product> parseProducts(final BufferedReader bufferedReader) throws IOException {
        List<Product> products = new ArrayList<>();
        String productInformation;
        bufferedReader.readLine();
        while ((productInformation = bufferedReader.readLine()) != null) {
            Product product = parseProduct(productInformation);
            products.add(product);
        }
        return products;
    }

    private static Product parseProduct(final String productInfo) {
        String[] split = productInfo.split(PRODUCT_INFORMATION_DELIMITER);
        return createProduct(split[0], parseInt(split[1]), parseInt(split[2]), split[3]);
    }

    private static Product createProduct(final String name, final int price, final int quantity,
                                         final String promotion) {
        if (promotion.equals(NO_PROMOTION)) {
            return new GeneralProduct(name, price, quantity);
        }
        return new PromotionProduct(name, price, quantity, promotion);
    }

    public static Map<String, Promotion> parsePromotions(final BufferedReader bufferedReader) throws IOException {
        Map<String, Promotion> promotions = new HashMap<>();
        String promotionInformation;
        bufferedReader.readLine();
        while ((promotionInformation = bufferedReader.readLine()) != null) {
            Promotion promotion = parsePromotion(promotionInformation);
            promotions.put(promotion.getName(), promotion);
        }
        return promotions;
    }

    private static Promotion parsePromotion(final String promotionInformation) {
        String[] split = promotionInformation.split(PROMOTION_INFORMATION_DELIMITER);
        String name = split[0];
        int buyQuantity = parseInt(split[1]);
        int bonusQuantity = parseInt(split[2]);
        LocalDate startDate = parseLocalDate(split[3]);
        LocalDate endDate = parseLocalDate(split[4]);
        return createPromotion(name, buyQuantity, bonusQuantity, startDate, endDate);
    }

    private static Promotion createPromotion(final String name, final int buyQuantity, final int bonusQuantity,
                                             final LocalDate startDate,
                                             final LocalDate endDate) {
        return new Promotion(name, buyQuantity, bonusQuantity, startDate, endDate);
    }

    public static int parseInt(final String number) {
        return Integer.parseInt(number);
    }

    public static LocalDate parseLocalDate(final String date) {
        return LocalDate.parse(date);
    }
}
