package store;

import static store.InputParser.parseInt;
import static store.InputParser.parseLocalDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import store.product.GeneralProduct;
import store.product.PromotionProduct;

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

    public static Map<String, PromotionProduct> parsePromotionProducts(
            final BufferedReader bufferedReader,
            final Map<String, Promotion> promotions) throws IOException {
        Map<String, PromotionProduct> promotionProducts = new HashMap<>();
        String productInformation = bufferedReader.readLine();
        while ((productInformation = bufferedReader.readLine()) != null) {
            String[] split = productInformation.split(PRODUCT_INFORMATION_DELIMITER);
            if (split[3].equals("null")) {
                continue;
            }
            PromotionProduct promotionProduct = getPromotionProduct(promotions, split);
            promotionProducts.put(promotionProduct.getName(), promotionProduct);
        }
        return Collections.unmodifiableMap(promotionProducts);
    }

    private static PromotionProduct getPromotionProduct(final Map<String, Promotion> promotions, final String[] split) {
        String name = split[0];
        int price = parseInt(split[1]);
        int quantity = parseInt(split[2]);
        String promotionName = split[3];
        Promotion promotion = promotions.get(promotionName);
        return new PromotionProduct(name, price, quantity, promotion);
    }

    public static Map<String, GeneralProduct> parseGeneralProducts(final BufferedReader bufferedReader)
            throws IOException {
        Map<String, GeneralProduct> generalProducts = new HashMap<>();
        String productInformation = bufferedReader.readLine();
        while ((productInformation = bufferedReader.readLine()) != null) {
            String[] split = productInformation.split(PRODUCT_INFORMATION_DELIMITER);
            if (!split[3].equals("null")) {
                continue;
            }
            GeneralProduct generalProduct = getGeneralProduct(split);
            generalProducts.put(generalProduct.getName(), generalProduct);
        }
        return generalProducts;
    }

    private static GeneralProduct getGeneralProduct(final String[] split) {
        String name = split[0];
        int price = parseInt(split[1]);
        int quantity = parseInt(split[2]);
        return new GeneralProduct(name, price, quantity);
    }
}
